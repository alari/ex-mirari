package mirari.own

import mirari.UtilController
import grails.plugins.springsecurity.Secured
import mirari.util.image.ImageResizer
import mirari.util.image.ImageCropPolicy
import mirari.validators.PasswordValidators
import grails.plugins.springsecurity.SpringSecurityService
import org.springframework.beans.factory.annotation.Autowired

@Secured("ROLE_USER")
class PersonPreferencesController extends UtilController{

  def fileStorageService
  def personPreferencesService

  def index = {
    [imageUrl: fileStorageService.getUrl(currentPerson.domain, "avatar.png")]
  }

  def uploadAvatar = {
     def f = request.getFile('avatar')
    if (!f || f.empty) {
      errorCode = "file is empty"
      redirect action: "index"
      return
    }

    File imFile = File.createTempFile("upload-avatar", ".tmp"+currentPerson.id.toString())
    f.transferTo(imFile)

    File avFile
    String avSize = "210*336"
    if(params.crop == "yes") {
      avFile = ImageResizer.createCropResized(imFile, avSize, ImageCropPolicy.CENTER)
    } else {
      avFile = ImageResizer.createResized(imFile, avSize)
    }
    fileStorageService.store(avFile, currentPerson.domain, "avatar.png")

    imFile.delete()
    avFile.delete()

    infoCode = "uploadAvatar has been called"
    redirect action: "index"
  }

  def changeEmail = {ChangeEmailCommand command ->
    alert personPreferencesService.setEmail(session, command)

    renderAlerts()
  }

  def applyEmailChange = {String t->
    alert personPreferencesService.applyEmailChange(session, t)
    redirect action: "index"
  }

  def changePassword = {ChangePasswordCommand command ->
    alert personPreferencesService.changePassword(command, currentPerson)

    renderAlerts()

    render template: "changePassword", model: [chPwdCommand: command]
  }
}

class ChangeEmailCommand{
  String email

  static constraints = {
    email email:true, blank: false
  }
}

class ChangePasswordCommand{
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
