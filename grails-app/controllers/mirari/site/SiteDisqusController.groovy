package mirari.site

import mirari.UtilController
import mirari.model.disqus.Comment
import mirari.model.disqus.Reply
import mirari.repo.CommentRepo
import mirari.repo.ReplyRepo
import ru.mirari.infra.feed.FeedQuery

class SiteDisqusController extends UtilController {

    CommentRepo commentRepo
    ReplyRepo replyRepo

    def comments(int page) {
        FeedQuery<Comment> feed = commentRepo.feed(_site).paginate(page, 25)
        [feed: feed]
    }

    def replies(int page) {
        FeedQuery<Reply> feed = replyRepo.feed(_site).paginate(page, 25)
        [feed: feed]
    }
}
