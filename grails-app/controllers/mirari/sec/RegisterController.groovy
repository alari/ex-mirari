package mirari.sec

import grails.plugins.springsecurity.Secured
import mirari.ServiceResponse
import mirari.UtilController
import mirari.morphia.subject.SubjectDAO
import mirari.validators.PasswordValidators
import org.springframework.beans.factory.annotation.Autowired

@Secured("IS_AUTHENTICATED_ANONYMOUSLY")
class RegisterController extends UtilController {

  static defaultAction = 'index'

  def registrationService

  def index = {RegisterCommand command ->
    Map model
    if (request.post) {
      ServiceResponse resp = registrationService.handleRegistration(command)
      alertsService.alert(flash, resp)
      model = resp.model
      model.put("command", command)
      return model
    } else {
      return [command: new RegisterCommand()]
    }
  }

  def verifyRegistration = {
    String token = params.t
    ServiceResponse result = registrationService.verifyRegistration(token)
    alertsService.alert(flash, result)

    redirect result.redirect
  }

  def forgotPassword = {

    if (!request.post) {
      // show the form
      render view: "/register/forgotPassword"
      return
    }

    ServiceResponse result = registrationService.handleForgotPassword(params.domain)
    alertsService.alert(flash, result)

    render view: "/register/forgotPassword", model: result.model
  }

  def resetPassword = { ResetPasswordCommand command ->

    String token = params.t

    ServiceResponse result = registrationService.handleResetPassword(token, command, request.method)
    alertsService.alert(flash, result)

    if (!result.ok) {
      if (result.redirect) {
        redirect result.redirect
      } else {
        render view: "/register/resetPassword", model: result.model
      }
    } else {
      redirect result.redirect
    }
  }
}

/**
 * @author Dmitry Kurinskiy
 * @since 18.08.11 23:02
 */
class RegisterCommand {

  @Autowired SubjectDAO subjectDao

  String domain
  String email
  String password
  String password2

  static constraints = {
    domain blank: false, validator: { value, command ->
      if (value) {
        if (command.subjectDao.domainExists(value)) {
          return 'registerCommand.domain.unique'
        }
        if (!((String) value).matches('^[-_a-zA-Z0-9]{4,16}$')) {
          return "registerCommand.domain.invalid"
        }
      }
    }
    email blank: false, email: true
    password blank: false, minSize: 8, maxSize: 64, validator: PasswordValidators.passwordValidator
    password2 validator: PasswordValidators.password2Validator
  }
}
