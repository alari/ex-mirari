package mirari

import mirari.morphia.sec.RegistrationCode
import mirari.morphia.sec.RegistrationCodeDAO
import mirari.morphia.subject.Person
import mirari.morphia.subject.PersonDAO
import mirari.morphia.subject.Role
import mirari.sec.RegisterCommand
import mirari.sec.ResetPasswordCommand
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
    ServiceResponse resp = new ServiceResponse()
    if (command.hasErrors()) {
      return resp.error("command validation error")
    }

    Person user = new Person(email: command.email, domain: command.domain,
        password: command.password, accountLocked: true, enabled: true)

    personDao.save(user)
    if (!user.id) {
      log.error "user not saved"
      return resp.error("user not saved")
    }
    /* TODO: validate
    if (!user.validate() || !user.save(flush: true)) {
      return new ServiceResponse(ok: false, alertCode: user.errors)
    }
    */

    RegistrationCode registrationCode = new RegistrationCode(domain: user.domain)
    registrationCodeDao.save(registrationCode)

    sendRegisterEmail(user, registrationCode.token)
    return resp.model(emailSent: true, token: registrationCode.token).success()
  }

  ServiceResponse verifyRegistration(String token) {
    // TODO: remove spring conf
    ServiceResponse result = new ServiceResponse().redirect(SpringSecurityUtils.securityConfig.successHandler.defaultTargetUrl)

    def conf = SpringSecurityUtils.securityConfig

    def registrationCode = token ? registrationCodeDao.getByToken(token) : null
    if (!registrationCode) {
      return result.error("register.error.badCode")
    }

    Person user

    user = personDao.getByDomain(registrationCode.domain)
    if (!user) {
      return result.error("register.error.userNotFound")
    }

    user.accountLocked = false
    for (roleName in conf.register.defaultRoleNames) {
      user.authorities.add(new Role(authority: roleName.toString()))
    }

    // TODO: this may fail
    personDao.save(user)


    registrationCodeDao.delete(registrationCode)

    if (result.alertCode) {
      return result
    }

    if (!user) {
      return result.error("register.error.badCode")
    }

    springSecurityService.reauthenticate user.domain

    return result.redirect(conf.register.postRegisterUrl ?: result.redirect).success("register.complete")
  }

  ServiceResponse handleForgotPassword(String domain) {
    ServiceResponse response = new ServiceResponse()
    if (!domain) {
      return response.warning('register.forgotPassword.username.missing')
    }

    Person user = personDao.getByDomain(domain)
    if (!user) {
      return response.error('register.forgotPassword.user.notFound')
    }

    RegistrationCode registrationCode = new RegistrationCode(domain: user.domain)
    registrationCodeDao.save(registrationCode)

    sendForgotPasswordEmail(user, registrationCode.token)
    return response.model(emailSent: true, token: registrationCode.token).info()
  }

  ServiceResponse handleResetPassword(String token, ResetPasswordCommand command, String requestMethod) {
    def registrationCode = token ? registrationCodeDao.getByToken(token) : null
    if (!registrationCode) {
      return new ServiceResponse().redirect(SpringSecurityUtils.securityConfig.successHandler.defaultTargetUrl).error('register.resetPassword.badCode')
    }

    if (!requestMethod.equalsIgnoreCase("post")) {
      return new ServiceResponse().model(token: token, command: new ResetPasswordCommand()).warning()
    }

    command.domain = registrationCode.domain
    command.validate()

    if (command.hasErrors()) {
      return new ServiceResponse().model(token: token, command: command).error()
    }

    // TODO: this may fail
    def user = personDao.getByDomain(registrationCode.domain)
    user.password = command.password
    // validate user account if it wasn't before
    if(user.accountLocked && user.authorities.size() == 0) {
      user.accountLocked = false
      for (roleName in SpringSecurityUtils.securityConfig.register.defaultRoleNames) {
        user.authorities.add(new Role(authority: roleName.toString()))
      }
    }
    personDao.save(user)

    registrationCodeDao.delete registrationCode

    springSecurityService.reauthenticate registrationCode.domain

    def conf = SpringSecurityUtils.securityConfig
    return new ServiceResponse().redirect(conf.register.postResetUrl ?: conf.successHandler.defaultTargetUrl).success('register.resetPassword.success')
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
