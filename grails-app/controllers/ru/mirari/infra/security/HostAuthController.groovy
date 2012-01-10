package ru.mirari.infra.security

import mirari.UtilController
import mirari.model.Site
import ru.mirari.infra.security.repo.SecurityCodeRepo

class HostAuthController extends UtilController{
    def springSecurityService
    def siteService
    SecurityCodeRepo securityCodeRepo

    def ask(String token) {
        SecurityCode code = securityCodeRepo.getByToken(token)
        if(code && code.host != request.getHeader("host")) {
            Site ref = siteService.getByHost(code.host)
            if(ref) {
                if(springSecurityService.isLoggedIn()) {
                    code.account = securityService.account
                    securityCodeRepo.save(code)
                } 
                redirect url: ref.getUrl(controller: controllerName, action: "reply")
            }
        }
    }

    def reply() {
        log.error  "Before for /-auth-host-reply, token: "+session.hostAuthToken

        SecurityCode code = securityCodeRepo.getByToken(session.hostAuthToken)
        if(code) {
            // validate
            if(code.host == request.getHeader("host") && code.account) {
                log.error ("Reauthenticating: "+code.account.email)
                springSecurityService.reauthenticate(code.account.email)
            }
            log.error "Redirecting to: "+"http://".concat(code.host).concat(code.url)
            redirect url: "http://".concat(code.host).concat(code.url)
            securityCodeRepo.delete(code)
        }
        session.hostAuthToken = null
    }
    
    def js(String id) {
        String referer = request.getHeader("referer")
        if (springSecurityService.principal?.id == id) referer = null
        if (!springSecurityService.isLoggedIn()) referer = null

        String href;
        if(referer) {
            URL url = new URL(referer)
            Site ref = siteService.getByHost(url.host)
            if(ref && ref != _portal) {
                    SecurityCode code = new SecurityCode(url: url.path, host: url.host, account: securityService.account)
                    securityCodeRepo.save(code)

                System.out.println("JS: Referer is ours: "+ref.host)
                href = ref.getUrl(controller: "hostAuth", action: "setSession", params: [token: code.token])
            }
        }
        render contentType: "application/javascript", text: href ? "window.location.href='".concat(href).concat("';") : "// nothing"
    }
    
    def setSession(String token) {
        System.out.println("JS: session for token: "+token)
        SecurityCode code = securityCodeRepo.getByToken(token)
        if(code && code.host == request.getHeader("host")) {
            System.out.println("JS: referer is: "+code.host.concat(code.url))
            springSecurityService.reauthenticate(code.account.email)
            redirect url: "http://".concat(code.host).concat(code.url)
            securityCodeRepo.delete(code)
        } else redirect url: ""
    }
}
