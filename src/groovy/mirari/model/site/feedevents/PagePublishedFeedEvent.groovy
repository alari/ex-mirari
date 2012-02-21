package mirari.model.site.feedevents

import mirari.event.EventType
import mirari.event.Event
import mirari.model.Page

/**
 * @author alari
 * @since 2/21/12 9:55 PM
 */
class PagePublishedFeedEvent extends PageFeedEvent{
    
    @Override
    boolean filter(EventType type) {
        type == EventType.PAGE_PUBLISHED
    }

    @Override
    void handle(Event event) {
        Page page = getPage(event)
        pageFeedRepo.countPlusPub(page.placedOnSites, page.type)
    }
}
