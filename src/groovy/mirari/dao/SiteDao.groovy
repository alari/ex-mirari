@Typed package mirari.dao

import mirari.model.Account
import mirari.model.Site
import mirari.repo.SiteRepo
import org.springframework.beans.factory.annotation.Autowired
import ru.mirari.infra.mongo.BaseDao
import ru.mirari.infra.mongo.MorphiaDriver
import com.google.code.morphia.Key
import mirari.repo.PageFeedRepo
import mirari.repo.PageRepo
import mirari.SiteInitService

/**
 * @author alari
 * @since 1/4/12 4:44 PM
 */
class SiteDao extends BaseDao<Site> implements SiteRepo {
    @Autowired SiteInitService siteInitService

    @Autowired SiteDao(MorphiaDriver morphiaDriver) {
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

    Iterable<Site> listByAccount(Account account) {
        createQuery().filter("account", account).fetch()
    }
    
    Key<Site> save(Site s) {
        boolean notPersisted = !s.isPersisted()
        Key<Site> key = super.save(s)
        if(notPersisted) {
            siteInitService.initSite(s)
        }
        key
    }
}