package mirari

import mirari.morphia.Site
import org.springframework.beans.factory.annotation.Autowired
import mirari.morphia.Site
import mirari.morphia.site.Portal
import mirari.morphia.site.Subsite
import javax.servlet.http.Cookie

class SiteFilters {

    @Autowired Site.Dao siteDao
    def alertsService

    def filters = {
        all(controller: "*", action: "*") {
            before = {
                String host = request.getHeader("host")
                if(host.startsWith("www.")) host = host.substring(4)
                
                Site site = siteDao.getByHost(host)
                if(!site) {
                    site = siteDao.getByName(host)
                    if(site) {
                        site.host = host
                        siteDao.save(site)
                    }
                }
                if(!site) {
                    // TODO: throw an exception, render exception without layout
                    alertsService.warning(flash, "error.siteNotFound")
                    redirect(uri: "")
                    return false
                }
                if(site instanceof Portal) {
                    request._portal = site
                } else if(site instanceof Subsite) {
                    request._site = site
                    request._portal = ((Subsite)site).portal ?: site
                }
            }
            after = {Map model ->
                if(model) {
                    model._site = request._site
                    model._portal = request._portal
                }
                if(session.new) {
                    Cookie c = new Cookie("JSESSIONID", session.id)
                    c.domain = ".".concat(request._portal.host)
                    c.path = "/"
                    response.addCookie(c)
                }
            }
        }
    }
}
