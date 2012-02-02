package mirari.dao

import ru.mirari.infra.mongo.BaseDao
import mirari.model.disqus.Comment
import ru.mirari.infra.mongo.MorphiaDriver
import mirari.repo.CommentRepo
import ru.mirari.infra.feed.FeedQuery
import mirari.model.Page
import org.springframework.beans.factory.annotation.Autowired

/**
 * @author alari
 * @since 2/2/12 3:41 PM
 */
class CommentDao extends BaseDao<Comment> implements CommentRepo{
    @Autowired
    CommentDao(MorphiaDriver morphiaDriver) {
        super(morphiaDriver)
    }

    @Override
    FeedQuery<Comment> listByPage(Page page) {
        new FeedQuery<Comment>(createQuery().filter("page", page).order("dateCreated"))
    }
}
