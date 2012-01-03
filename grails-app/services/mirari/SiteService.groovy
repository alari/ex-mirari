package mirari

import mirari.morphia.Site

class SiteService {

    static transactional = false
    
    Site.Dao siteDao
    
    Site getByHost(String host) {
        // TODO: cache somewhere!
        if(host.startsWith("www.")) host = host.substring(4)

        Site site = siteDao.getByHost(host)
        if(!site) {
            site = siteDao.getByName(host)
            if(site) {
                site.host = host
                siteDao.save(site)
            }
        }
        site
    }
}
