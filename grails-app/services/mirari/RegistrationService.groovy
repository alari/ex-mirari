package mirari

import mirari.sec.RegisterCommand

import mirari.sec.ResetPasswordCommand

import mirari.morphia.sec.RegistrationCodeDAO
import mirari.morphia.sec.RegistrationCode
import mirari.morphia.subject.PersonDAO
import mirari.morphia.subject.Person
import mirari.morphia.subject.Role

import org.apache.log4j.Logger
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.springframework.beans.factory.annotation.Autowired

class RegistrationService {
  static transactional = false
  private Logger log = Logger.getLogger(getClass())

  def springSecurityService
  def mailSenderService
  @Autowired I18n i18n
  @Autowired
  RegistrationCodeDAO registrationCodeDao
  @Autowired
  PersonDAO personDao

  ServiceResponse handleRegistration(RegisterCommand command) {
    ServiceResponse resp = new ServiceResponse(ok: false)
    if (command.hasErrors()) {
      return resp.setAttributes(model: [commmand: command], messageCode: "errors")
    }

    Person user = new Person(email: command.email, domain: command.domain,
        password: command.password, accountLocked: true, enabled: true)

    personDao.save(user)
    if (!user.id) {
      log.error "user not saved"
      return resp.setAttributes(model: [commmand: command], messageCode: "user not saved")
    }
    /* TODO: validate
    if (!user.validate() || !user.save(flush: true)) {
      return new ServiceResponse(ok: false, messageCode: user.errors)
      // TODO
    }
    */

    RegistrationCode registrationCode = new RegistrationCode(domain: user.domain)
    registrationCodeDao.save(registrationCode)

    sendRegisterEmail(user, registrationCode.token)
    return new ServiceResponse(ok: true, model: [emailSent: true, token: registrationCode.token])
  }

  ServiceResponse verifyRegistration(String token) {
    ServiceResponse result = new ServiceResponse(redirectUri: SpringSecurityUtils.securityConfig.successHandler.defaultTargetUrl)

    def conf = SpringSecurityUtils.securityConfig

    def registrationCode = token ? registrationCodeDao.getByToken(token) : null
    if (!registrationCode) {
      return result.setAttributes(ok: false, messageCode: "register.error.badCode")
    }

    Person user

    user = personDao.getByDomain(registrationCode.domain)
    if (!user) {
      return result.setAttributes(ok: false, messageCode: "register.error.userNotFound")
    }

    user.accountLocked = false
    for (roleName in conf.ui.register.defaultRoleNames) {
      user.authorities.add(new Role(authority: roleName.toString()))
    }

    // TODO: this may fail
    personDao.save(user)


    registrationCodeDao.delete(registrationCode)

    if (result.messageCode) {
      return result
    }

    if (!user) {
      return result.setAttributes(ok: false, messageCode: "register.error.badCode")
    }

    springSecurityService.reauthenticate user.domain

    return result.setAttributes(
        ok: true,
        messageCode: "register.complete",
        redirectUri: conf.ui.register.postRegisterUrl ?: result.redirectUri
    )
  }

  ServiceResponse handleForgotPassword(String domain) {
    ServiceResponse response = new ServiceResponse()
    if (!domain) {
      return response.setAttributes(ok: false, messageCode: 'register.forgotPassword.username.missing')
    }

    Person user = personDao.getByDomain(domain)
    if (!user) {
      return response.setAttributes(ok: false, messageCode: 'register.forgotPassword.user.notFound')
    }

    RegistrationCode registrationCode = new RegistrationCode(domain: user.domain)
    registrationCodeDao.save(registrationCode)

    return response.setAttributes(ok: sendForgotPasswordEmail(user, registrationCode.token))
  }

  ServiceResponse handleResetPassword(String token, ResetPasswordCommand command, String requestMethod) {
    def registrationCode = token ? registrationCodeDao.getByToken(token) : null
    if (!registrationCode) {
      return new ServiceResponse(
          messageCode: 'register.resetPassword.badCode',
          ok: false,
          redirectUri: SpringSecurityUtils.securityConfig.successHandler.defaultTargetUrl)
    }

    if (!requestMethod.equalsIgnoreCase("post")) {
      return new ServiceResponse(ok: false, model: [token: token, command: new ResetPasswordCommand()])
    }

    command.domain = registrationCode.domain
    command.validate()

    if (command.hasErrors()) {
      return new ServiceResponse(ok: false, model: [token: token, command: command])
    }

    // TODO: this may fail
    def user = personDao.getByDomain(registrationCode.domain)
    user.password = command.password
    personDao.save(user)

    registrationCodeDao.delete registrationCode

    springSecurityService.reauthenticate registrationCode.domain

    def conf = SpringSecurityUtils.securityConfig
    return new ServiceResponse(
        ok: true,
        messageCode: 'register.resetPassword.success',
        redirectUri: conf.ui.register.postResetUrl ?: conf.successHandler.defaultTargetUrl)
  }

  private boolean sendRegisterEmail(Person person, String token) {
    mailSenderService.putMessage(
        to: person.email,
        subject: i18n."register.confirm.emailSubject",
        view: "/mail-messages/confirmEmail",
        model: [username: person.domain, token: token]
    )
    true
  }

  private boolean sendForgotPasswordEmail(Person person, String token) {
    mailSenderService.putMessage(
        to: person.email,
        subject: i18n."register.forgotPassword.emailSubject",
        view: "/mail-messages/forgotPassword",
        model: [username: person.domain, token: token]
    )
    true
  }
}
