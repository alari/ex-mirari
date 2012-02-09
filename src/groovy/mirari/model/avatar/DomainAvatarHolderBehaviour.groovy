@Typed package mirari.model.avatar

import mirari.event.EventType
import mirari.repo.AvatarRepo
import mirari.util.ApplicationContextHolder

/**
 * @author alari
 * @since 2/9/12 3:33 PM
 */
class DomainAvatarHolderBehaviour implements AvatarHolder {
    static private final AvatarRepo avatarRepo

    static {
        avatarRepo = (AvatarRepo) ApplicationContextHolder.getBean("avatarRepo")
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
}
