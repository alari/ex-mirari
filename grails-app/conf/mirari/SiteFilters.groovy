package mirari

import javax.servlet.http.Cookie
import mirari.model.Site
import mirari.model.site.Portal
import mirari.model.site.Subsite
import ru.mirari.infra.security.SecurityCode
import ru.mirari.infra.security.repo.SecurityCodeRepo

class SiteFilters {
    def alertsService
    def springSecurityService
    def securityService
    def siteService
    SecurityCodeRepo securityCodeRepo
    
    def filters = {
        all(controller: "*", action: "*") {
            before = {
                Site site = siteService.getByHost(request.getHeader("host"))
                Site mainPortal = siteService.getMainPortal()
                if(!site) {
                    // TODO: throw an exception, render exception without layout
                    alertsService.warning(flash, "error.siteNotFound")
                    log.error("Host not found: "+request.getHeader("host")+" ("+request.forwardURI+"), referer: "+request.getHeader("referer"))
                    redirect(uri: mainPortal.getUrl())
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
                    model._mainPortal = siteService.getMainPortal()
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
