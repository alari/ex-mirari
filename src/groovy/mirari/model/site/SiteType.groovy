package mirari.model.site

import mirari.model.Site

/**
 * @author alari
 * @since 1/28/12 2:11 PM
 */
public enum SiteType {
    PORTAL("portal"),
    PROFILE("profile");

    final String name
    
    SiteType(String n) {
        name = n
    }
    
    void setSiteName(Site site) {
        if (site.type == PORTAL) {
            site.host = site.name
        } else {    //site.head shall not be empty
            site.host = site.name?.concat(".")?.concat(site.head.portal.host)
        }
    }
}