package mirari

import mirari.morphia.space.Subject
import org.springframework.web.multipart.MultipartFile
import mirari.util.image.ImageFormat
import mirari.util.image.ImageStorage
import mirari.morphia.Space

class AvatarService {

    static transactional = false

    ImageStorage imageStorage

    final ImageFormat AVATAR_LARGE = new ImageFormat("210x336", "ava-large")

    String getUrl(Space space, ImageFormat format) {
        imageStorage.getUrl(format, space.path)
    }

    ServiceResponse uploadSpaceAvatar(MultipartFile f, Space space) {
        ServiceResponse resp = new ServiceResponse().redirect(action: "index")

        if (!f || f.empty) {
            return resp.error("file is empty")
        }

        File imFile = File.createTempFile("upload-avatar", ".tmp")
        f.transferTo(imFile)

        imageStorage.formatAndDelete([AVATAR_LARGE], imFile, space.path)

        resp.success("uploadAvatar has been called")
    }


}
