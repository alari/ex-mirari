@Typed package mirari.morphia.site

import mirari.morphia.Site
import mirari.morphia.Account
import ru.mirari.infra.mongo.BaseDao
import org.springframework.beans.factory.annotation.Autowired
import ru.mirari.infra.mongo.MorphiaDriver
import com.google.code.morphia.annotations.Indexed
import com.google.code.morphia.annotations.Reference

/**
 * @author alari
 * @since 12/12/11 3:05 PM
 */
class Profile extends Site{
    @Indexed
    @Reference
    Account account
    
    static public class Dao extends BaseDao<Profile> {
        @Autowired Dao(MorphiaDriver morphiaDriver){
            super(morphiaDriver)
        }
        
        Iterable<Profile> listByAccount(Account account) {
            createQuery().filter("account", account).fetch()
        }
    }
}
