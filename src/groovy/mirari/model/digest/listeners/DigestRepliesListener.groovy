package mirari.model.digest.listeners

import mirari.event.EventListenerBean
import mirari.event.EventType
import mirari.model.disqus.Reply
import org.springframework.beans.factory.annotation.Autowired
import mirari.repo.ReplyRepo
import mirari.repo.NoticeRepo
import mirari.event.Event
import mirari.model.Page
import mirari.model.disqus.Comment
import mirari.model.digest.Notice
import mirari.model.digest.NoticeType

/**
 * @author alari
 * @since 3/9/12 12:22 AM
 */
class DigestRepliesListener extends EventListenerBean{
    @Autowired ReplyRepo replyRepo
    @Autowired NoticeRepo noticeRepo
    
    @Override
    boolean filter(EventType type) {
        type == EventType.DOMAIN_PERSIST    }

    @Override
    boolean filter(final Event event) {
        event.params.className == Reply.canonicalName
    }

    private Reply getReply(final Event event) {
        replyRepo.getById((String)event.params._id)
    }
        
    @Override
    void handle(final Event event) {
        final Reply reply = getReply(event)
        
        final Page page = reply.page
        final Comment comment = reply.comment
        
        if(comment.owner != reply.owner) {
            Notice commentNotice = new Notice()
            commentNotice.type = NoticeType.COMMENT_REPLY
            commentNotice.reason = reply
            commentNotice.owner = comment.owner
            noticeRepo.save(commentNotice)
            if(comment.owner == page.owner) return;
        }
        
        if(page.owner != reply.owner) {
            Notice pageNotice = new Notice()
            pageNotice.type = NoticeType.PAGE_REPLY
            pageNotice.reason = reply
            pageNotice.owner = page.owner
            noticeRepo.save(pageNotice)
        }
    }
}
