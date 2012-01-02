package mirari.site

import org.springframework.beans.factory.annotation.Autowired
import mirari.morphia.Site
import mirari.UtilController

abstract class SiteUtilController extends UtilController {
    @Autowired Site.Dao siteDao

    protected String get_siteName() {
        _site.name
    }

    protected Site get_site() {
        request._site
    }
}
