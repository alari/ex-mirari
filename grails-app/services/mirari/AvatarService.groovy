package mirari

import mirari.model.avatar.Avatar
import mirari.model.Site
import mirari.model.avatar.AvatarHolder
import mirari.repo.AvatarRepo
import mirari.util.ServiceResponse
import org.springframework.web.multipart.MultipartFile
import ru.mirari.infra.image.ImageFormat
import ru.mirari.infra.mongo.BaseDao
import ru.mirari.infra.file.FileInfo
import grails.util.Environment

class AvatarService {

    static transactional = false

    def imageStorageService
    AvatarRepo avatarRepo

    String getUrl(AvatarHolder holder, ImageFormat format = null) {
        if (holder.avatar) return imageStorageService.getUrl(holder.avatar, format)
    }

    ServiceResponse uploadSiteAvatar(MultipartFile f, Site site, BaseDao holderDao) {
        ServiceResponse resp = new ServiceResponse()

        if (!f || f.empty) {
            return resp.error("file is empty")
        }

        File imFile = File.createTempFile("upload-avatar", ".tmp")
        f.transferTo(imFile)

        if (!site.avatar || site.avatar.basic) {
            site.avatar = new Avatar(basic: false)
            avatarRepo.save(site.avatar)
            holderDao.save(site)
        }

        imageStorageService.format(site.avatar, imFile)

        resp.success("uploadAvatar has been called")
    }

    void uploadBasicAvatar(File f, String name, boolean ignoreIfNotModified = false) {
        Avatar avatar = avatarRepo.getBasic(name)
        if (!avatar) {
            avatar = new Avatar(
                    basic: true,
                    name: name
            )
            avatarRepo.save(avatar)
        } else if (ignoreIfNotModified) {
            if (avatar.lastUpdated > new Date(f.lastModified())) {
                return
            }
        }
        
        println "Updating avatar '${name}'..."

        avatarRepo.save(avatar)
        try {
            imageStorageService.format(avatar, f, [], !ignoreIfNotModified)
        } catch (Exception e) {
            log.error(e)
        }
    }
    
    void uploadDefaultBasics() {
        new File((Environment.isWarDeployed() ? "" : "web-app/").concat("images/basic")).eachFile {f->
            FileInfo fileInfo = new FileInfo(f)
            if(fileInfo.mediaType == "image") {
                uploadBasicAvatar(f, fileInfo.title, true)
            }
        }
    }
}
