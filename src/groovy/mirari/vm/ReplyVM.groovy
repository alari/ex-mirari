package mirari.vm

import mirari.model.disqus.Reply

/**
 * @author alari
 * @since 2/16/12 3:49 PM
 */
class ReplyVM {
    String id

    Date dateCreated
    String text
    String html

    OwnerVM owner

    static ReplyVM build(final Reply reply) {
        new ReplyVM(reply)
    }

    private ReplyVM(final Reply reply) {
        id = reply.stringId
        dateCreated = reply.dateCreated
        text = reply.text
        html = reply.html
        owner = OwnerVM.build(reply.owner)
    }

    ReplyVM(){}
}
