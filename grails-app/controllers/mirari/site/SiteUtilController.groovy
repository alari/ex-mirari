package mirari.site

import mirari.UtilController
import mirari.model.Site

abstract class SiteUtilController extends UtilController {

    protected String get_siteName() {
        _site.name
    }

    protected Site get_site() {
        request._site
    }
}
