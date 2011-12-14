package mirari

import ru.mirari.infra.image.ImageFormat
import org.springframework.web.multipart.MultipartFile

import mirari.morphia.Avatar
import mirari.morphia.face.AvatarHolder
import ru.mirari.infra.mongo.MorphiaDriver
import ru.mirari.infra.mongo.BaseDao

class AvatarService {

    static transactional = false

    def imageStorageService
    Avatar.Dao avatarDao

    String getUrl(AvatarHolder holder, ImageFormat format = null) {
        if(holder.avatar) return imageStorageService.getUrl(holder.avatar, format)
    }

    ServiceResponse uploadSiteAvatar(MultipartFile f, AvatarHolder holder, BaseDao holderDao) {
        ServiceResponse resp = new ServiceResponse()

        if (!f || f.empty) {
            return resp.error("file is empty")
        }

        File imFile = File.createTempFile("upload-avatar", ".tmp")
        f.transferTo(imFile)

        if (!holder.avatar || holder.avatar.basic) {
            holder.avatar = new Avatar(basic: false)
            avatarDao.save(holder.avatar)
            holderDao.save(holder)
        }
        
        imageStorageService.format(holder.avatar, imFile)

        resp.success("uploadAvatar has been called")
    }
    
    void uploadBasicAvatar(File f, String name) {
        Avatar avatar = avatarDao.getByName(name)
        if(!avatar) {
            avatar = new Avatar(
                    basic: true,
                    name: name
            )
            avatarDao.save(avatar)
        }
        
        imageStorageService.format(avatar, f)
    }
}
