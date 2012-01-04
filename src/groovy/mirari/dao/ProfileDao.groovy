@Typed package mirari.dao

import mirari.model.Account
import ru.mirari.infra.mongo.MorphiaDriver
import org.springframework.beans.factory.annotation.Autowired
import mirari.repo.ProfileRepo
import mirari.model.site.Profile
import ru.mirari.infra.mongo.BaseDao

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