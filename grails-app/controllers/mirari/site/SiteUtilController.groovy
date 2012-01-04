package mirari.site

import mirari.model.Site
import mirari.UtilController

abstract class SiteUtilController extends UtilController {

    protected String get_siteName() {
        _site.name
    }

    protected Site get_site() {
        request._site
    }
}
