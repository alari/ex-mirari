package mirari.model.page.thumb

import mirari.event.Event
import mirari.event.EventType
import mirari.model.Page

/**
 * @author alari
 * @since 2/9/12 6:05 PM
 */
class PageInnerThumbChange extends ThumbChange {
    @Override
    boolean filter(EventType eventType) {
        // TODO: create a special event to catch only image units change...
        eventType == EventType.DOMAIN_POST_PERSIST
    }

    boolean filter(Event event) {
        Page.class.canonicalName == event.params.className
    }

    private Page getPage(Event event) {
        pageRepo.getById((String) event.params._id)
    }

    @Override
    void handle(Event event) {
        Page page = getPage(event)
        if (page.thumbOrigin > ThumbOrigin.PAGE_INNER_IMAGE) {
            return;
        }
        String thumbSrc = getInnerImageThumbSrc(page)
        if (thumbSrc) {
            if (thumbSrc == page.thumbSrc) {
                return
            }
            pageRepo.setThumbSrc(page, thumbSrc, ThumbOrigin.PAGE_INNER_IMAGE)
            return
        }

        if (page.thumbOrigin < ThumbOrigin.PAGE_INNER_IMAGE) {
            if (page.thumbSrc) return;
        }
        if (!page.owner.avatar.basic) {
            pageRepo.setThumbSrc(page, page.owner.avatar.srcThumb, ThumbOrigin.OWNER_AVATAR)
            return;
        }
        pageRepo.setThumbSrc(page, avatarRepo.getBasic(page.type.name).srcThumb, ThumbOrigin.TYPE_DEFAULT)
    }
}
