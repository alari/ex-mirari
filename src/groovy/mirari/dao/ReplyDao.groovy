package mirari.dao

import mirari.model.Page
import mirari.model.disqus.Comment
import mirari.model.disqus.Reply
import mirari.repo.ReplyRepo
import org.springframework.beans.factory.annotation.Autowired
import ru.mirari.infra.feed.FeedQuery
import ru.mirari.infra.mongo.BaseDao
import ru.mirari.infra.mongo.MorphiaDriver

/**
 * @author alari
 * @since 2/2/12 5:45 PM
 */
class ReplyDao extends BaseDao<Reply> implements ReplyRepo {
    @Autowired
    ReplyDao(MorphiaDriver morphiaDriver) {
        super(morphiaDriver)
    }

    @Override
    FeedQuery<Reply> listByPage(Page page) {
        new FeedQuery<Reply>(createQuery().filter("page", page).order("dateCreated"))
    }

    @Override
    FeedQuery<Reply> listByComment(Comment comment) {
        new FeedQuery<Reply>(createQuery().filter("comment", comment).order("dateCreated"))
    }
}
