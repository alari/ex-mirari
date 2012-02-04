package mirari

import mirari.model.Avatar
import mirari.model.Site
import mirari.model.face.AvatarHolder
import mirari.repo.AvatarRepo
import mirari.util.ServiceResponse
import org.springframework.web.multipart.MultipartFile
import ru.mirari.infra.image.ImageFormat
import ru.mirari.infra.mongo.BaseDao

class AvatarService {

    static transactional = false

    def imageStorageService
    AvatarRepo avatarRepo

    String getUrl(AvatarHolder holder, ImageFormat format = null) {
        if (holder.avatar) return imageStorageService.getUrl(holder.avatar, format)
    }

    String getUrl(Site site, ImageFormat format = null) {
        getUrl(site.head, format)
    }

    ServiceResponse uploadSiteAvatar(MultipartFile f, Site site, BaseDao holderDao) {
        ServiceResponse resp = new ServiceResponse()

        if (!f || f.empty) {
            return resp.error("file is empty")
        }

        File imFile = File.createTempFile("upload-avatar", ".tmp")
        f.transferTo(imFile)

        if (!site.head.avatar || site.head.avatar.basic) {
            site.head.avatar = new Avatar(basic: false)
            avatarRepo.save(site.head.avatar)
            holderDao.save(site)
        }

        imageStorageService.format(site.head.avatar, imFile)

        resp.success("uploadAvatar has been called")
    }

    void uploadBasicAvatar(File f, String name) {
        Avatar avatar = avatarRepo.getByName(name)
        if (!avatar) {
            avatar = new Avatar(
                    basic: true,
                    name: name
            )
            avatarRepo.save(avatar)
        }

        imageStorageService.format(avatar, f)
    }
}
