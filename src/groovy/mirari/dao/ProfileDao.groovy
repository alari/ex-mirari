@Typed package mirari.dao

import mirari.model.Account
import mirari.model.site.Profile
import mirari.repo.ProfileRepo
import org.springframework.beans.factory.annotation.Autowired
import ru.mirari.infra.mongo.BaseDao
import ru.mirari.infra.mongo.MorphiaDriver

/**
 * @author alari
 * @since 1/4/12 4:41 PM
 */
class ProfileDao extends BaseDao<Profile> implements ProfileRepo{
@Autowired ProfileDao(MorphiaDriver morphiaDriver){
super(morphiaDriver)
}

Iterable<Profile> listByAccount(Account account) {
    createQuery().filter("account", account).fetch()
}
}