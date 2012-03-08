package mirari.dao

import ru.mirari.infra.mongo.BaseDao
import mirari.model.digest.Notice
import mirari.repo.NoticeRepo
import ru.mirari.infra.mongo.MorphiaDriver
import org.springframework.beans.factory.annotation.Autowired
import ru.mirari.infra.feed.FeedQuery
import mirari.model.Site

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
    FeedQuery<Notice> feed(Site owner) {
        new FeedQuery<Notice>(createQuery().filter("owner", owner))
    }
}
