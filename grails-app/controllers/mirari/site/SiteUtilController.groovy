package mirari.site

import org.springframework.beans.factory.annotation.Autowired
import mirari.morphia.Site
import mirari.UtilController

abstract class SiteUtilController extends UtilController {
    @Autowired Site.Dao siteDao

    protected String getCurrentSiteName() {
        params.siteName
    }

    protected Site getCurrentSite() {
        params.site
    }
}
