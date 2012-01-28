package mirari.model.site

import mirari.model.Account
import com.google.code.morphia.annotations.Indexed
import com.google.code.morphia.annotations.Reference
import mirari.model.Site
import com.google.code.morphia.annotations.Embedded

/**
 * @author alari
 * @since 1/28/12 2:15 PM
 */
@Embedded
class SiteHead {
    @Indexed
    @Reference(lazy=true)
    Account account

    @Indexed
    @Reference(lazy=true)
    Site portal
}
