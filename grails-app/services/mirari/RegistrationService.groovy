package mirari

import mirari.morphia.sec.RegistrationCode
import mirari.morphia.sec.RegistrationCodeDAO
import mirari.morphia.subject.Person
import mirari.morphia.subject.PersonDAO
import mirari.morphia.subject.Role
import mirari.sec.RegisterCommand
import mirari.sec.ResetPasswordCommand
import mirari.util.ConfigReader
import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired

class RegistrationService {
  static transactional = false
  private Logger log = Logger.getLogger(getClass())

  def springSecurityService
  def mailSenderService
  I18n i18n
  RegistrationCodeDAO registrationCodeDao
  PersonDAO personDao

  def grailsApplication

  private ConfigObject getConf() {
    grailsApplication.config
  }

  /**
   * Tries to register a user
   *
   * @param command
   * @return
   */
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

    RegistrationCode registrationCode = new RegistrationCode(domain: user.domain)
    registrationCodeDao.save(registrationCode)

    sendRegisterEmail(user, registrationCode.token)
    return resp.model(emailSent: true, token: registrationCode.token).success()
  }

  /**
   * Verifies registration (user email) by token
   *
   * @param token
   * @return
   */
  ServiceResponse verifyRegistration(String token) {
    ServiceResponse result = new ServiceResponse().redirect(conf.grails.mirari.sec.url.defaultTarget)

    def registrationCode = token ? registrationCodeDao.getByToken(token) : null
    if (!registrationCode) {
      return result.error("register.error.badCode")
    }

    Person user

    user = personDao.getByDomain(registrationCode.domain)
    if (!user) {
      return result.error("register.error.userNotFound")
    }
    setDefaultRoles(user)

    personDao.save(user)

    registrationCodeDao.delete(registrationCode)

    if (result.alertCode) {
      return result
    }

    if (!user) {
      return result.error("register.error.badCode")
    }

    springSecurityService.reauthenticate user.domain
    return result.redirect(conf.grails.mirari.sec.url.emailVerified).success("register.complete")
  }

  /**
   * Sends an forgot-password email
   *
   * @param domain
   * @return
   */
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

  /**
   * Resets the password for user
   *
   * @param token
   * @param command
   * @param requestMethod
   * @return
   */
  ServiceResponse handleResetPassword(String token, ResetPasswordCommand command, String requestMethod) {
    def registrationCode = token ? registrationCodeDao.getByToken(token) : null
    if (!registrationCode) {
      return new ServiceResponse().redirect(conf.grails.mirari.sec.url.defaultTarget).error('register.resetPassword.badCode')
    }

    if (!requestMethod.equalsIgnoreCase("post")) {
      return new ServiceResponse().model(token: token, command: new ResetPasswordCommand()).warning()
    }

    command.domain = registrationCode.domain
    command.validate()

    if (command.hasErrors()) {
      return new ServiceResponse().model(token: token, command: command).error()
    }

    def user = personDao.getByDomain(registrationCode.domain)
    if (!user || !user instanceof Person) {
      return new ServiceResponse().model(token: token, command: new ResetPasswordCommand()).warning('register.forgotPassword.user.notFound')
    }
    user.password = command.password

    // validate user account if it wasn't before
    if (user.accountLocked && user.authorities.size() == 0) {
      setDefaultRoles(user)
    }
    personDao.save(user)

    registrationCodeDao.delete registrationCode

    springSecurityService.reauthenticate registrationCode.domain

    return new ServiceResponse().redirect(
        conf.grails.mirari.sec.url.passwordResetted
        ?: conf.grails.mirari.sec.url.defaultTarget
    ).success('register.resetPassword.success')
  }

  private setDefaultRoles(Person user) {
    user.accountLocked = false
    for (roleName in conf.grails.mirari.sec.defaultRoleNames) {
      log.error "Adding authority: ${roleName}"
      user.authorities.add(new Role(authority: roleName.toString()))
    }
  }

  /**
   * Sending register email routine
   *
   * @param person
   * @param token
   * @return
   */
  private boolean sendRegisterEmail(Person person, String token) {
    mailSenderService.putMessage(
        to: person.email,
        subject: i18n."register.confirm.emailSubject",
        view: "/mail-messages/confirmEmail",
        model: [username: person.domain, token: token]
    )
    true
  }

  /**
   * Sending password reminder email routine
   *
   * @param person
   * @param token
   * @return
   */
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
