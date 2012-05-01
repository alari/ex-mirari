package mirari.model.digest.listeners

import mirari.event.EventListenerBean
import mirari.event.EventType
import org.springframework.beans.factory.annotation.Autowired
import mirari.repo.FollowRepo
import mirari.model.Follow
import mirari.model.digest.NoticeBuilder
import mirari.repo.NoticeRepo

/**
 * @author alari
 * @since 3/31/12 2:24 AM
 */
class FollowerNewListener extends EventListenerBean {
    @Autowired FollowRepo followRepo
    @Autowired NoticeBuilder noticeBuilder
    @Autowired NoticeRepo noticeRepo
    
    @Override
    boolean filter(final mirari.event.EventType type) {
        type == EventType.FOLLOWER_NEW
    }

    @Override
    void handle(final mirari.event.Event event) {
        final Follow follow = followRepo.getById((String)event.params._id)
        noticeRepo.save noticeBuilder.followerNew(follow)
    }
}
