package mirari.model.digest.listeners

import mirari.event.EventListenerBean
import mirari.event.EventType
import mirari.event.Event
import mirari.repo.FollowRepo
import mirari.model.Page
import mirari.model.Site
import org.springframework.beans.factory.annotation.Autowired
import mirari.model.digest.NoticeBuilder
import mirari.repo.PageRepo
import mirari.repo.NoticeRepo

/**
 * @author alari
 * @since 3/31/12 1:54 AM
 */
class FollowNewPagesListener extends EventListenerBean{
    @Autowired FollowRepo followRepo
    @Autowired NoticeBuilder noticeBuilder
    @Autowired PageRepo pageRepo
    @Autowired NoticeRepo noticeRepo
    
    Page getPage(final Event event) {
        pageRepo.getById((String)event.params._id)
    }
    
    @Override
    boolean filter(final EventType type) {
        type == EventType.PAGE_PUBLISHED
    }

    @Override
    void handle(final Event event) {
        Page page = getPage(event)
        List<Site> followers = followRepo.followers(page.owner)
        for (Site follower in followers) {
            noticeRepo.save noticeBuilder.followPagePublished(page, follower)
        }
    }
}
