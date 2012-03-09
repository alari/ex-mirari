package mirari.vm

import mirari.model.digest.NoticeType
import mirari.model.disqus.Reply
import mirari.model.disqus.Comment

/**
 * @author alari
 * @since 3/9/12 10:44 PM
 */
class ReasonVM {
    OwnerVM actor

    private static ReasonVM get(final NoticeType type) {
        ReasonVM reason = new ReasonVM()
        reason
    }
    
    static ReasonVM build(final Reply reply, final NoticeType type) {
        ReasonVM reason = get(type)
        reason.actor = OwnerVM.build reply.owner
        reason
    }
    
    static ReasonVM build(final Comment comment, final NoticeType type) {
        ReasonVM reason = get(type)
        reason.actor = OwnerVM.build comment.owner
        reason
    }

    ReasonVM(){}
}
