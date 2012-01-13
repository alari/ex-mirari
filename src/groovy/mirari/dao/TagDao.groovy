package mirari.dao

import ru.mirari.infra.mongo.BaseDao
import mirari.model.Tag
import mirari.repo.TagRepo
import mirari.model.Site
import ru.mirari.infra.mongo.MorphiaDriver
import org.springframework.beans.factory.annotation.Autowired

/**
 * @author alari
 * @since 1/13/12 6:34 PM
 */
class TagDao extends BaseDao<Tag> implements TagRepo{
    @Autowired
    TagDao(MorphiaDriver morphiaDriver) {
        super(morphiaDriver)
    }

    Tag getByDisplayNameAndSite(String displayName, Site site) {
        createQuery().filter("displayName", displayName).filter("site", site).get()
    }
}
