package mirari.model

import com.google.code.morphia.annotations.Entity
import com.google.code.morphia.annotations.Reference

/**
 * @author alari
 * @since 12/27/11 3:03 PM
 */
@Entity("security.account")
class Account extends ru.mirari.infra.security.Account {

    @Reference(lazy = true) Site mainProfile

    @Deprecated
    static public class Dao {
    }
}
