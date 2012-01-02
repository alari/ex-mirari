import mirari.morphia.Site
import mirari.morphia.site.Portal
import mirari.ApplicationContextHolder

class BootStrap {
    def init = { servletContext ->

        Site.Dao siteDao = (Site.Dao)ApplicationContextHolder.getBean("siteDao")

        String mainHost = ApplicationContextHolder.getBean("mainPortalHost")

        if(!siteDao.getByHost(mainHost)) {
            Portal portal = new Portal(host: mainHost, name: mainHost, displayName: mainHost)
            siteDao.save(portal)
        }
    }
    def destroy = {
    }
}
