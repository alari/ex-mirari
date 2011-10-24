package mirari.own

import grails.plugins.springsecurity.Secured
import grails.plugins.springsecurity.SpringSecurityService
import mirari.ServiceResponse
import mirari.UtilController
import mirari.validators.PasswordValidators
import org.springframework.beans.factory.annotation.Autowired

@Secured("ROLE_USER")
class PersonPreferencesController extends UtilController {

  def fileStorageService
  def personPreferencesService

  def index = {
    [imageUrl: fileStorageService.getUrl(currentPerson.domain, "avatar.png")]
  }


  def uploadAvatar = {
    if (request.post) {
      def f = request.getFile('avatar')
      ServiceResponse resp = personPreferencesService.uploadAvatar(f, currentPerson)
      //alert resp

      render([thumbnail: fileStorageService.getUrl(currentPerson.domain, "avatar.png"), alertCode: resp.alertCode].encodeAsJSON())

      //[{"name":"picture1.jpg","size":902604,"url":"\/\/example.org\/files\/picture1.jpg","thumbnail_url":"\/\/example.org\/thumbnails\/picture1.jpg","delete_url":"\/\/example.org\/upload-handler?file=picture1.jpg","delete_type":"DELETE"},{"name":"picture2.jpg","size":841946,"url":"\/\/example.org\/files\/picture2.jpg","thumbnail_url":"\/\/example.org\/thumbnails\/picture2.jpg","delete_url":"\/\/example.org\/upload-handler?file=picture2.jpg","delete_type":"DELETE"}]

      // redirect resp.redirect
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
