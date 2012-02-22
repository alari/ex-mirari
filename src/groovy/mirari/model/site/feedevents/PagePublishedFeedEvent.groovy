package mirari.model.site.feedevents

import mirari.event.Event
import mirari.event.EventType
import mirari.model.Page
import mirari.model.page.PageType
import mirari.repo.SiteRepo
import org.springframework.beans.factory.annotation.Autowired

/**
 * @author alari
 * @since 2/21/12 9:55 PM
 */
class PagePublishedFeedEvent extends PageFeedEvent {

    @Autowired SiteRepo siteRepo

    @Override
    boolean filter(EventType type) {
        type in [EventType.PAGE_DELETED, EventType.PAGE_DRAFT_CHANGED, EventType.DOMAIN_PERSIST]
    }

    @Override
    boolean filter(Event event) {
        if(event.type == EventType.DOMAIN_PERSIST) {
            return event.params.className == Page.class.canonicalName
        }
        true
    }

    @Override
    void handle(Event event) {
        switch (event.type) {
            case EventType.PAGE_DELETED:
                pageFeedRepo.updateCounts(event.params.sites.collect {siteRepo.getById(it)}.asList(), PageType.getByName((String) event.params.type)) {
                    it.dec("countAll").dec(event.params.draft ? "countDrafts" : "countPubs")
                }
                break;

            case EventType.PAGE_DRAFT_CHANGED:
                Page page = getPage(event)
                pageFeedRepo.updateCounts(page.placedOnSites, page.type) {
                    if (event.params.draft) {
                        it.inc("countDrafts").dec("countPubs")
                    } else {
                        it.dec("countDrafts").inc("countPubs")
                    }
                }
                break;

            case EventType.DOMAIN_PERSIST:
                Page page = getPage(event)
                pageFeedRepo.updateCounts(page.placedOnSites, page.type) {
                    it.inc("countAll").inc(page.draft ? "countDrafts" : "countPubs")
                }
                break;
        }
    }
}
