package mirari.vm

import mirari.model.disqus.Comment
import mirari.model.disqus.Reply

/**
 * @author alari
 * @since 2/16/12 3:52 PM
 */
class CommentVM {
    String id

    Date dateCreated
    String text
    String title
    String html
    List<ReplyVM> replies

    OwnerVM owner

    static CommentVM build(final Comment comment, final Iterable<Reply> repliesIterator = []) {
        new CommentVM(comment, repliesIterator)
    }

    private CommentVM(final Comment comment, final Iterable<Reply> repliesIterator = []) {
        id = comment.stringId
        title = comment.title
        dateCreated = comment.dateCreated
        text = comment.text
        html = comment.html
        replies = []
        for (Reply r: repliesIterator) {
            replies.add ReplyVM.build(r)
        }
        owner = OwnerVM.build(comment.owner)
    }

    CommentVM(){}
}
