package mirari.model.image.thumb

import mirari.event.Event
import mirari.event.EventType
import mirari.model.Page
import mirari.model.image.CommonImage

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
        if (page.image.origin > ThumbOrigin.PAGE_INNER_IMAGE) {
            return;
        }
        CommonImage image = getInnerImage(page)
        if (image) {
            if (image.thumbSrc == page.image.thumbSrc) {
                return
            }
            pageRepo.setImage(page, image, ThumbOrigin.PAGE_INNER_IMAGE)
            return
        }

        if (page.image.origin < ThumbOrigin.PAGE_INNER_IMAGE) {
            if (page.image.thumbSrc) return;
        }
        if (!page.owner.avatar.basic) {
            pageRepo.setImage(page, page.owner.avatar, ThumbOrigin.OWNER_AVATAR)
            return;
        }
        pageRepo.setImage(page, avatarRepo.getBasic(page.type.name), ThumbOrigin.TYPE_DEFAULT)
    }
}
