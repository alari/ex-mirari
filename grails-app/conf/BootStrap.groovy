import mirari.model.site.Portal
import mirari.repo.SiteRepo
import mirari.util.ApplicationContextHolder
import mirari.dao.PageDao

class BootStrap {
    def init = { servletContext ->
        SiteRepo siteRepo = (SiteRepo)ApplicationContextHolder.getBean("siteRepo")

        String mainHost = ApplicationContextHolder.config.mirari.mainPortal.host
        String mainTitle = ApplicationContextHolder.config.mirari.mainPortal.displayName

        if(!siteRepo.getByHost(mainHost)) {
            Portal portal = new Portal(host: mainHost, name: mainHost, displayName: mainTitle)
            siteRepo.save(portal)
        }
    }
    def destroy = {
    }
}
