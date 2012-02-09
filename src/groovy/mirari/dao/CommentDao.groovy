package mirari.dao

import com.mongodb.WriteResult
import mirari.model.Page
import mirari.model.disqus.Comment
import mirari.model.disqus.Reply
import mirari.repo.CommentRepo
import mirari.repo.ReplyRepo
import org.springframework.beans.factory.annotation.Autowired
import ru.mirari.infra.feed.FeedQuery
import ru.mirari.infra.mongo.BaseDao
import ru.mirari.infra.mongo.MorphiaDriver

/**
 * @author alari
 * @since 2/2/12 3:41 PM
 */
class CommentDao extends BaseDao<Comment> implements CommentRepo {
    @Autowired ReplyRepo replyRepo

    @Autowired CommentDao(MorphiaDriver morphiaDriver) {
        super(morphiaDriver)
    }

    @Override
    FeedQuery<Comment> listByPage(Page page) {
        new FeedQuery<Comment>(createQuery().filter("page", page).order("dateCreated"))
    }

    WriteResult delete(Comment comment) {
        for (Reply r: replyRepo.listByComment(comment)) {
            replyRepo.delete(r)
        }
        super.delete(comment)
    }
}
