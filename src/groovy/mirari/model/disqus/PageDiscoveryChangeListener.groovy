package mirari.model.disqus

import mirari.event.Event
import mirari.event.EventListenerBean
import mirari.event.EventType
import mirari.model.Page
import mirari.repo.CommentRepo
import mirari.repo.PageRepo
import mirari.repo.ReplyRepo
import org.springframework.beans.factory.annotation.Autowired

/**
 * @author alari
 * @since 2/14/12 5:28 PM
 */
class PageDiscoveryChangeListener extends EventListenerBean {
    @Autowired ReplyRepo replyRepo
    @Autowired CommentRepo commentRepo
    @Autowired PageRepo pageRepo

    @Override
    boolean filter(EventType type) {
        type == EventType.PAGE_PLACED_ON_SITES_CHANGED || type == EventType.PAGE_DRAFT_CHANGED
    }

    private Page getPage(final Event event) {
        pageRepo.getById((String) event.params._id)
    }

    @Override
    void handle(final Event event) {
        final Page page = getPage(event)
        replyRepo.updatePageDiscovery(page)
        commentRepo.updatePageDiscovery(page)
    }
}
