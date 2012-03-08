package mirari.dao

import ru.mirari.infra.mongo.BaseDao
import mirari.model.digest.Notice
import mirari.repo.NoticeRepo
import ru.mirari.infra.mongo.MorphiaDriver
import org.springframework.beans.factory.annotation.Autowired
import ru.mirari.infra.feed.FeedQuery
import mirari.model.Site
import org.bson.types.ObjectId

/**
 * @author alari
 * @since 3/9/12 12:01 AM
 */
class NoticeDao extends BaseDao<Notice> implements NoticeRepo{
    @Autowired
    NoticeDao(MorphiaDriver morphiaDriver) {
        super(morphiaDriver)
    }

    @Override
    FeedQuery<Notice> feed(final Site owner) {
        new FeedQuery<Notice>(createQuery().filter("owner", owner).order("-dateCreated"))
    }

    @Override
    FeedQuery<Notice> feedUnwatched(final Site owner) {
        new FeedQuery<Notice>(createQuery().filter("owner", owner).filter("watched", false).order("-dateCreated"))
    }

    @Override
    long countUnwatched(final Site owner) {
        createQuery().filter("owner", owner).filter("watched", false).countAll()
    }

    @Override
    void watch(final Site owner, String id) {
        updateFirst(
                createQuery().filter("id", new ObjectId(id)).filter("owner", owner),
                createUpdateOperations().set("watched", true)
        )
    }
}
