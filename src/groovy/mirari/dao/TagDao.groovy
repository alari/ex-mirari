package mirari.dao

import mirari.model.Site
import mirari.model.Tag
import mirari.repo.TagRepo
import org.springframework.beans.factory.annotation.Autowired
import ru.mirari.infra.mongo.BaseDao
import ru.mirari.infra.mongo.MorphiaDriver

/**
 * @author alari
 * @since 1/13/12 6:34 PM
 */
class TagDao extends BaseDao<Tag> implements TagRepo {
    @Autowired TagDao(MorphiaDriver morphiaDriver) {
        super(morphiaDriver)
    }

    Tag getByDisplayNameAndSite(String displayName, Site site) {
        createQuery().filter("displayName", displayName).filter("site", site).get()
    }

    @Override
    Iterable<Tag> listBySite(Site site) {
        createQuery().filter("site", site).fetch()
    }
}
