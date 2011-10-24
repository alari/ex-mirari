package mirari.own

import grails.plugins.springsecurity.Secured
import grails.plugins.springsecurity.SpringSecurityService
import mirari.ServiceResponse
import mirari.UtilController
import mirari.validators.PasswordValidators
import org.springframework.beans.factory.annotation.Autowired
import mirari.ImageFormat

@Secured("ROLE_USER")
class PersonPreferencesController extends UtilController {

  def personPreferencesService
  def avatarService

  def index = {
    [imageUrl: avatarService.getUrl(currentPerson, ImageFormat.AVATAR_LARGE)]
  }

  def uploadAvatar = {
    if (request.post) {
      def f = request.getFile('avatar')
      ServiceResponse resp =  avatarService.uploadSubjectAvatar(f, currentPerson)
      render([thumbnail: avatarService.getUrl(currentPerson, ImageFormat.AVATAR_LARGE), alertCode: resp.alertCode].encodeAsJSON())
    }
  }

  def changeEmail = {ChangeEmailCommand command ->
    alert personPreferencesService.setEmail(session, command)

    renderAlerts()
  }

  def applyEmailChange = {String t ->
    alert personPreferencesService.applyEmailChange(session, t)
    redirect action: "index"
  }

  def changePassword = {ChangePasswordCommand command ->
    alert personPreferencesService.changePassword(command, currentPerson)

    renderAlerts()

    render template: "changePassword", model: [chPwdCommand: command]
  }
}

class ChangeEmailCommand {
  String email

  static constraints = {
    email email: true, blank: false
  }
}

class ChangePasswordCommand {
  String domain
  String oldPassword
  String password
  String password2

  @Autowired
  SpringSecurityService springSecurityService

  static constraints = {
    domain blank: false
    oldPassword blank: false
    password blank: false, minSize: 7, maxSize: 64, validator: PasswordValidators.passwordValidator
    password2 validator: PasswordValidators.password2Validator
  }
}
