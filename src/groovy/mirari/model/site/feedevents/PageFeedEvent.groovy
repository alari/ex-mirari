package mirari.model.site.feedevents

import mirari.event.EventListenerBean
import org.springframework.beans.factory.annotation.Autowired
import mirari.repo.PageRepo
import mirari.repo.PageFeedRepo
import mirari.model.Page
import mirari.event.Event

/**
 * @author alari
 * @since 2/21/12 9:59 PM
 */
abstract class PageFeedEvent extends EventListenerBean {
    @Autowired PageRepo pageRepo
    @Autowired PageFeedRepo pageFeedRepo

    protected Page getPage(Event event) {
        pageRepo.getById((String)event.params._id)
    }
}
