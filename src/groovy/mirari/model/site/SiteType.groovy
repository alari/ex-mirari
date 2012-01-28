package mirari.model.site

import mirari.model.Site

/**
 * @author alari
 * @since 1/28/12 2:11 PM
 */
public enum SiteType {
    PORTAL,
    PROFILE;

    void setSiteName(Site site) {
        if(site.type == PORTAL) {
            site.host = site.name
        } else {
            site.host = site.name.concat(".").concat(site.head.portal.host)
        }
    }
}