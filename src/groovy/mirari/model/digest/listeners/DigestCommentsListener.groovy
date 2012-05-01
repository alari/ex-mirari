package mirari.model.digest.listeners

import mirari.event.EventListenerBean
import mirari.event.EventType
import mirari.event.Event
import mirari.model.disqus.Comment
import org.springframework.beans.factory.annotation.Autowired
import mirari.repo.CommentRepo
import mirari.model.digest.Notice
import mirari.model.digest.NoticeType
import mirari.repo.NoticeRepo
import mirari.model.digest.NoticeBuilder

/**
 * @author alari
 * @since 3/8/12 11:56 PM
 */
class DigestCommentsListener extends EventListenerBean{
    @Autowired CommentRepo commentRepo
    @Autowired NoticeRepo noticeRepo
    @Autowired NoticeBuilder noticeBuilder
    
    @Override
    boolean filter(EventType type) {
        type == EventType.DOMAIN_PERSIST
    }
    
    @Override
    boolean filter(final Event event) {
        event.params.className == Comment.canonicalName
    }

    private Comment getComment(final Event event) {
        commentRepo.getById((String)event.params._id)
    }
    
    @Override
    void handle(final Event event) {
        final Comment comment = getComment(event)

        if(comment.owner == comment.page.owner) {
            return;
        }

        Notice notice = noticeBuilder.pageComment(comment)

        noticeRepo.save(notice)
    }
}
