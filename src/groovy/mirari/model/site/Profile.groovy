@Typed package mirari.model.site

import com.google.code.morphia.annotations.Indexed
import com.google.code.morphia.annotations.Reference
import mirari.model.Account

/**
 * @author alari
 * @since 12/12/11 3:05 PM
 */
class Profile extends Subsite {
    @Indexed
    @Reference
    Account account
}
