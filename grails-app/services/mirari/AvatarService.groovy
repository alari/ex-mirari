package mirari

import mirari.morphia.space.Subject
import org.springframework.web.multipart.MultipartFile
import mirari.util.image.ImageFormat
import mirari.util.image.ImageStorage

class AvatarService {

    static transactional = false

    ImageStorage imageStorage

    final ImageFormat AVATAR_LARGE = new ImageFormat("210x336", "ava-large")

    String getUrl(Subject subject, ImageFormat format) {
        imageStorage.getUrl(format, subject.path)
    }

    ServiceResponse uploadSubjectAvatar(MultipartFile f, Subject subject) {
        ServiceResponse resp = new ServiceResponse().redirect(action: "index")

        if (!f || f.empty) {
            return resp.error("file is empty")
        }

        File imFile = File.createTempFile("upload-avatar", ".tmp")
        f.transferTo(imFile)

        imageStorage.formatAndDelete([AVATAR_LARGE], imFile, subject.path)

        resp.success("uploadAvatar has been called")
    }


}
