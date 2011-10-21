package mirari.own

import mirari.UtilController
import grails.plugins.springsecurity.Secured

@Secured("ROLE_USER")
class PersonPreferencesController extends UtilController{

  def imageFileService
  def fileStorageService

  def index = {

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
    if(params.crop == "yes") {
      avFile = imageFileService.createCroppedThumb(imFile, 320, 200, params.int("cropZone") as byte)
    } else {
      avFile = imageFileService.createThumb(imFile, 320, 200)
    }
    fileStorageService.store(avFile, "im", "test.png")

    imFile.delete()
    avFile.delete()

    infoCode = "uploadAvatar has been called"
    redirect action: "index"
  }
}
