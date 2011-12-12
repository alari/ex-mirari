package mirari

import ru.mirari.infra.image.ImageFormat
import org.springframework.web.multipart.MultipartFile
import mirari.morphia.Site

class AvatarService {

    static transactional = false

    def imageStorageService

    String getUrl(Site space, ImageFormat format = null) {
        imageStorageService.getUrl(space, format)
    }

    ServiceResponse uploadSpaceAvatar(MultipartFile f, Site space) {
        ServiceResponse resp = new ServiceResponse().redirect(action: "index")

        if (!f || f.empty) {
            return resp.error("file is empty")
        }

        File imFile = File.createTempFile("upload-avatar", ".tmp")
        f.transferTo(imFile)

        imageStorageService.format(space, imFile)

        resp.success("uploadAvatar has been called")
    }
}
