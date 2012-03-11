package mirari.vm

import mirari.model.digest.NoticeType
import mirari.model.disqus.Reply
import mirari.model.disqus.Comment
import mirari.model.digest.Notice

/**
 * @author alari
 * @since 3/9/12 10:44 PM
 */
class ReasonVM {
    String url

    CommentVM comment
    ReplyVM reply

    private static ReasonVM get(final Notice notice) {
        ReasonVM reason = new ReasonVM()
        reason
    }
    
    static ReasonVM build(final Reply reply, final Notice notice) {
        ReasonVM reason = get(notice)
        reason.reply = ReplyVM.build(reply)
        reason.comment = CommentVM.build(reply.comment)
        reason.url = notice?.page?.getUrl()+"#comments"
        reason
    }
    
    static ReasonVM build(final Comment comment, final Notice notice) {
        ReasonVM reason = get(notice)
        reason.comment = CommentVM.build(comment)
        reason.url = notice?.page?.getUrl()+"#comments"
        reason
    }

    ReasonVM(){}
}
