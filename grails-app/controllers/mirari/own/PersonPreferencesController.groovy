package mirari.own

import mirari.UtilController
import grails.plugins.springsecurity.Secured
import mirari.util.image.ImageResizer
import mirari.util.image.ImageCropPolicy

@Secured("ROLE_USER")
class PersonPreferencesController extends UtilController{

  def fileStorageService

  def index = {
    [imageUrl: fileStorageService.getUrl("im", "test.png")]
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
      avFile = ImageResizer.createCropResized(imFile, "200x320", ImageCropPolicy.CENTER)
    } else {
      avFile = ImageResizer.createResized(imFile, "200x320")
    }
    fileStorageService.store(avFile, "im", "test.png")

    imFile.delete()
    avFile.delete()

    infoCode = "uploadAvatar has been called"
    redirect action: "index"
  }
}
