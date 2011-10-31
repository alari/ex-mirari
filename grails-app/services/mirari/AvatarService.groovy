package mirari

import mirari.morphia.space.Subject
import org.springframework.web.multipart.MultipartFile
import mirari.util.image.ImageFormat

class AvatarService {

    static transactional = false

    def fileStorageService

    final ImageFormat AVATAR_LARGE = new ImageFormat("210x336", "ava-large")

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

        //ImageFormat format = new ImageFormat.AVATAR_LARGE

        File avFile = AVATAR_LARGE.format((File)imFile)
        store(avFile, subject, AVATAR_LARGE)

        imFile.delete()
        avFile.delete()

        resp.success("uploadAvatar has been called")
    }


}
