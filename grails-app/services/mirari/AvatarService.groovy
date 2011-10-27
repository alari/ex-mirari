package mirari

import mirari.morphia.space.Subject
import mirari.util.image.ImageResizer
import org.springframework.web.multipart.MultipartFile

class AvatarService {

    static transactional = false

    def fileStorageService

    String getUrl(Subject subject, ImageFormat format) {
        fileStorageService.getUrl(subject.name, format.name + ".png")
    }

    void store(File file, Subject subject, ImageFormat format) {
        fileStorageService.store(file, subject.name, format.name + ".png")
    }

    ServiceResponse uploadSubjectAvatar(MultipartFile f, Subject subject) {
        ServiceResponse resp = new ServiceResponse().redirect(action: "index")

        if (!f || f.empty) {
            return resp.error("file is empty")
        }

        File imFile = File.createTempFile("upload-avatar", ".tmp")
        f.transferTo(imFile)

        ImageFormat format = ImageFormat.AVATAR_LARGE

        File avFile = ImageResizer.createCropResized(imFile, format.size, format.cropPolicy)
        store(avFile, subject, format)

        imFile.delete()
        avFile.delete()

        resp.success("uploadAvatar has been called")
    }


}
