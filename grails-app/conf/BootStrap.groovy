
import mirari.repo.SiteRepo
import mirari.util.ApplicationContextHolder
import mirari.model.site.SiteType
import mirari.model.Site

class BootStrap {
    def init = { servletContext ->
        SiteRepo siteRepo = (SiteRepo)ApplicationContextHolder.getBean("siteRepo")

        String mainHost = ApplicationContextHolder.config.mirari.mainPortal.host
        String mainTitle = ApplicationContextHolder.config.mirari.mainPortal.displayName

        if(!siteRepo.getByHost(mainHost)) {
            Site portal = new Site(type: SiteType.PORTAL, host: mainHost, name: mainHost, displayName: mainTitle)
            siteRepo.save(portal)
        }
    }
    def destroy = {
    }
}
