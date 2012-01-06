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
                if(!site) {
                    // TODO: throw an exception, render exception without layout
                    alertsService.warning(flash, "error.siteNotFound")
                    redirect(uri: "")
                    return false
                }
                
                // check referer
                if(session.new && !springSecurityService.isLoggedIn()) {
                    //System.out.println("Session is new, checking referer...")
                   // System.out.println("Host = "+request.getHeader("host"))
                    String referer = request.getHeader("referer")
                    if(referer) {
                        referer = new URL(referer).host
                        Site ref = siteService.getByHost(referer)
                        if(ref) {
                            if(site?.id == ref?.id) {
                                System.out.println("Current site is a ref site; cant redirect")
                            } else {
                                SecurityCode code = new SecurityCode(url: request.forwardURI, host: request.getHeader("host"))
                                securityCodeRepo.save(code)
                                
                                session.hostAuthToken = code.token

                                //System.out.println("Referer is ours: "+ref.host)
                                
                                redirect uri: ref.getUrl(controller: "hostAuth", action: "ask", params: [token: code.token])
                                return false;
                            }
                        }
                    }                    
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
