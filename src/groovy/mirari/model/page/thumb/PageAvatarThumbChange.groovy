package mirari.model.page.thumb

import mirari.event.Event
import mirari.event.EventType
import mirari.model.Page
import mirari.model.avatar.Avatar
import org.apache.log4j.Logger

/**
 * @author alari
 * @since 2/9/12 5:42 PM
 */
class PageAvatarThumbChange extends ThumbChange {
    private final Logger log = Logger.getLogger(this.class)

    boolean filter(EventType eventType) {
        eventType == EventType.PAGE_AVATAR_CHANGED
    }

    private Page getPage(Event event) {
        pageRepo.getById((String) event.params._id)
    }

    void handle(Event event) {
        Page page = getPage(event)
        if (!page) {
            log.error("Page not found: " + event)
            return
        }
        Avatar avatar = getAvatar(event)
        int thumbOrigin
        String thumbSrc

        if (!avatar.basic) {
            thumbSrc = avatar.srcThumb
            thumbOrigin = ThumbOrigin.PAGE_AVATAR
        } else {
            thumbSrc = getInnerImageThumbSrc(page)
            if (thumbSrc) {
                thumbOrigin = ThumbOrigin.PAGE_INNER_IMAGE
            } else if (!page.owner.avatar.basic) {
                thumbOrigin = ThumbOrigin.OWNER_AVATAR
                thumbSrc = page.owner.avatar.srcThumb
            } else {
                thumbSrc = avatar.srcThumb
                thumbOrigin = ThumbOrigin.TYPE_DEFAULT
            }
        }
        pageRepo.setThumbSrc(page, thumbSrc, thumbOrigin)
    }
}
