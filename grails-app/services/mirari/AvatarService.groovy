package mirari

import mirari.model.avatar.Avatar
import mirari.model.avatar.AvatarHolder
import mirari.repo.AvatarRepo
import mirari.util.ServiceResponse
import org.springframework.web.multipart.MultipartFile
import ru.mirari.infra.image.ImageFormat
import ru.mirari.infra.file.FileInfo
import grails.util.Environment
import ru.mirari.infra.persistence.Repo
import org.springframework.core.io.ClassPathResource

class AvatarService {

    static transactional = false

    def imageStorageService
    AvatarRepo avatarRepo

    String getUrl(AvatarHolder holder, ImageFormat format = null) {
        if (holder.avatar) return imageStorageService.getUrl(holder.avatar, format)
    }

    ServiceResponse uploadHolderAvatar(MultipartFile f, AvatarHolder domain, Repo repo) {
        ServiceResponse resp = new ServiceResponse()

        domain.avatarMultipartFile = f
        repo.save(domain)

        resp.model(avatar: domain.avatar.viewModel).success()
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
            log.error("Failed updating avatar ${name}!", e)
        }
    }
    
    void uploadDefaultBasics() {
        try {
            File dir = new ClassPathResource("basic-avatars", mirari.AvatarService).file
            if(!dir.isDirectory()) {
                println dir.absolutePath+" is not directory!"
                return
            }
            dir.eachFile {f->
                FileInfo fileInfo = new FileInfo(f)
                if(fileInfo.mediaType == "image") {
                    uploadBasicAvatar(f, fileInfo.title, true)
                }
            }
        } catch (Exception e) {
            println e
        }
    }
}
