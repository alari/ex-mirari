package mirari.model.digest

import mirari.model.disqus.Comment
import mirari.model.disqus.Reply
import mirari.model.Page
import mirari.model.Site

/**
 * @author alari
 * @since 3/11/12 9:16 PM
 */
class NoticeBuilder {
    Notice commentReply(final Comment comment, final Reply reply) {
        Notice notice = new Notice()
        
        notice.type = NoticeType.COMMENT_REPLY
        notice.reason = reply
        notice.owner = comment.owner
        notice.page = comment.page
        
        notice
    }
    
    Notice pageComment(final Comment comment) {
        Notice notice = new Notice()

        notice.owner = comment.page.owner
        notice.reason = comment
        notice.type = NoticeType.PAGE_COMMENT
        notice.page = comment.page
        notice
    }
    
    Notice pageReply(final Reply reply){
        Notice notice = new Notice()

        notice.type = NoticeType.PAGE_REPLY
        notice.reason = reply
        notice.owner = reply.page.owner
        notice.page = reply.page

        notice
    }
    
    Notice followPagePublished(final Page page, final Site follower) {
        Notice notice = new Notice()

        notice.page = page
        notice.owner = follower
        notice.type = NoticeType.FOLLOW_PAGE

        notice
    }
}
