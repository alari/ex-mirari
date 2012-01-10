package mirari

import mirari.model.Site
import mirari.repo.SiteRepo
import mirari.model.site.Portal

class SiteService {

    static transactional = false
    
    SiteRepo siteRepo
    def grailsApplication
    
    Site getByHost(String host) {
        // TODO: cache somewhere!
        if(host.startsWith("www.")) host = host.substring(4)

        Site site = siteRepo.getByHost(host)
        if(!site) {
            site = siteRepo.getByName(host)
            if(site) {
                site.host = host
                siteRepo.save(site)
            }
        }
        site
    }
    
    Portal getMainPortal() {
        (Portal)getByHost(grailsApplication.config.mirari.mainPortal.host)
    }
}
