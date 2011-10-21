package mirari.own

import mirari.UtilController
import grails.plugins.springsecurity.Secured
import mirari.util.image.ImageResizer
import mirari.util.image.ImageCropPolicy

@Secured("ROLE_USER")
class PersonPreferencesController extends UtilController{

  def fileStorageService

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
}
