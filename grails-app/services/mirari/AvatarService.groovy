package mirari

import ru.mirari.infra.image.ImageFormat
import org.springframework.web.multipart.MultipartFile
import mirari.morphia.Site

class AvatarService {

    static transactional = false

    def imageStorageService

    String getUrl(Site site, ImageFormat format = null) {
        imageStorageService.getUrl(site, format)
    }

    ServiceResponse uploadSiteAvatar(MultipartFile f, Site site) {
        ServiceResponse resp = new ServiceResponse().redirect(action: "index")

        if (!f || f.empty) {
            return resp.error("file is empty")
        }

        File imFile = File.createTempFile("upload-avatar", ".tmp")
        f.transferTo(imFile)

        imageStorageService.format(site, imFile)

        resp.success("uploadAvatar has been called")
    }
}
