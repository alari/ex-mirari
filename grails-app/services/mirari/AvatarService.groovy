package mirari

import org.springframework.web.multipart.MultipartFile
import mirari.util.image.ImageFormat
import mirari.util.image.ImageStorage
import mirari.morphia.Space

class AvatarService {

    static transactional = false

    ImageStorage imageStorage

    String getUrl(Space space, ImageFormat format=null) {
        imageStorage.getUrl(space, format)
    }

    ServiceResponse uploadSpaceAvatar(MultipartFile f, Space space) {
        ServiceResponse resp = new ServiceResponse().redirect(action: "index")

        if (!f || f.empty) {
            return resp.error("file is empty")
        }

        File imFile = File.createTempFile("upload-avatar", ".tmp")
        f.transferTo(imFile)

        imageStorage.format(space, imFile)

        resp.success("uploadAvatar has been called")
    }


}
