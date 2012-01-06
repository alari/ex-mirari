import mirari.model.site.Portal
import mirari.repo.SiteRepo
import mirari.util.ApplicationContextHolder

class BootStrap {
    def init = { servletContext ->

        SiteRepo siteRepo = (SiteRepo)ApplicationContextHolder.getBean("siteRepo")

        String mainHost = ApplicationContextHolder.getBean("mainPortalHost")

        if(!siteRepo.getByHost(mainHost)) {
            Portal portal = new Portal(host: mainHost, name: mainHost, displayName: mainHost)
            siteRepo.save(portal)
        }
    }
    def destroy = {
    }
}
