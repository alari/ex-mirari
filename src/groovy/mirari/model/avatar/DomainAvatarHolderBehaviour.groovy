@Typed package mirari.model.avatar

import mirari.event.EventType
import mirari.repo.AvatarRepo
import mirari.util.ApplicationContextHolder
import org.springframework.web.multipart.MultipartFile
import ru.mirari.infra.image.ImageStorageService

/**
 * @author alari
 * @since 2/9/12 3:33 PM
 */
class DomainAvatarHolderBehaviour implements AvatarHolder {
    static private final AvatarRepo avatarRepo
    static private final ImageStorageService imageStorageService

    static {
        avatarRepo = (AvatarRepo) ApplicationContextHolder.getBean("avatarRepo")
        imageStorageService = (ImageStorageService) ApplicationContextHolder.getBean("imageStorageService")
    }

    private final AvatarHolderDomain domain
    private final EventType eventType

    DomainAvatarHolderBehaviour(final AvatarHolderDomain domain) {
        this.domain = domain
    }

    DomainAvatarHolderBehaviour(final AvatarHolderDomain domain, final EventType eventType) {
        this.domain = domain
        this.eventType = eventType
    }

    @Override
    Avatar getAvatar() {
        domain._avatar ?: avatarRepo.getBasic(domain.basicAvatarName)
    }

    @Override
    void setAvatar(Avatar o) {
        if (!o || o.basic) {
            domain._avatar = null
        } else {
            domain._avatar = o
        }
        if (eventType) {
            domain.firePostPersist(eventType, [avatarId: o?.stringId, basicName: domain.basicAvatarName])
        }
    }

    @Override
    void setAvatarFile(File f) {
        if (!domain._avatar) {
            domain._avatar = new Avatar(basic: false)
            avatarRepo.save(domain._avatar)
        }
        domain.firePostPersist(eventType, [avatarId: domain._avatar?.stringId, basicName: domain.basicAvatarName])

        imageStorageService.format(domain._avatar, f)
    }

    @Override
    void setAvatarMultipartFile(MultipartFile f) {
        if (!f || f.empty) {
            return
        }

        File imFile = File.createTempFile("upload-avatar", ".tmp")
        f.transferTo(imFile)

        setAvatarFile(imFile)
    }
}
