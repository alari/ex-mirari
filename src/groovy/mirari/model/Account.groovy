package mirari.model

import com.google.code.morphia.annotations.Entity
import com.google.code.morphia.annotations.Reference
import mirari.model.site.Profile

/**
 * @author alari
 * @since 12/27/11 3:03 PM
 */
@Entity("security.account")
class Account extends ru.mirari.infra.security.Account {

    @Reference(lazy = true) Profile mainProfile

    @Deprecated
    static public class Dao {
    }
}
