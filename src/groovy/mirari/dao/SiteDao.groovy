@Typed package mirari.dao

import ru.mirari.infra.mongo.BaseDao
import mirari.repo.SiteRepo
import org.springframework.beans.factory.annotation.Autowired
import ru.mirari.infra.mongo.MorphiaDriver
import mirari.model.Site

/**
 * @author alari
 * @since 1/4/12 4:44 PM
 */
class SiteDao extends BaseDao<Site> implements SiteRepo{
    @Autowired SiteDao(MorphiaDriver morphiaDriver){
        super(morphiaDriver)
    }

    Site getByName(String name) {
        createQuery().filter("name", name.toLowerCase()).get()
    }

    boolean nameExists(String name) {
        createQuery().filter("name", name.toLowerCase()).countAll() > 0
    }

    Site getByHost(String host) {
        createQuery().filter("host", host.toLowerCase()).get()
    }

    boolean hostExists(String host) {
        createQuery().filter("host", host.toLowerCase()).countAll() > 0
    }
}