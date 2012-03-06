package mirari.model.image.thumb

import mirari.event.Event
import mirari.event.EventType
import mirari.model.Site
import mirari.model.avatar.Avatar
import mirari.repo.SiteRepo
import org.springframework.beans.factory.annotation.Autowired

/**
 * @author alari
 * @since 2/9/12 6:04 PM
 */
class PageOwnerThumbChange extends ThumbChange {
    @Autowired SiteRepo siteRepo

    boolean filter(EventType eventType) {
        eventType == EventType.SITE_AVATAR_CHANGED
    }

    private Site getSite(Event event) {
        siteRepo.getById((String) event.params._id)
    }

    @Override
    void handle(Event event) {
        Avatar avatar = getAvatar(event)
        if (avatar.basic) {
            pageRepo.setImage(getSite(event))
        } else {
            pageRepo.setImage(getSite(event), avatar)
        }
    }
}
