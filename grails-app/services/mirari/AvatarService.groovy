package mirari

import org.springframework.web.multipart.MultipartFile
import mirari.morphia.subject.Person
import mirari.util.image.ImageResizer

class AvatarService {

  def fileStorageService

  String getUrl(Person person, ImageFormat format) {
    fileStorageService.getUrl(person.domain, format+".png")
  }

  void store(File file, Person person, ImageFormat format) {
    fileStorageService.store(file, person.domain, format+".png")
  }

  ServiceResponse uploadPersonAvatar(MultipartFile f, Person currentPerson) {
        ServiceResponse resp = new ServiceResponse().redirect(action: "index")

    if (!f || f.empty) {
      return resp.error("file is empty")
    }

    File imFile = File.createTempFile("upload-avatar", ".tmp")
    f.transferTo(imFile)

    ImageFormat format = ImageFormat.AVATAR_LARGE

    File avFile = ImageResizer.createCropResized(imFile, format.size, format.cropPolicy)
    store(avFile, currentPerson, format)

    imFile.delete()
    avFile.delete()

    resp.success("uploadAvatar has been called")
  }


}
