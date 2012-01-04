package ru.mirari.infra.security

import mirari.model.Site
import mirari.UtilController
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
        //System.out.println "Before for /-auth-host-reply, token: "+session.hostAuthToken

        SecurityCode code = securityCodeRepo.getByToken(session.hostAuthToken)
        if(code) {
            // validate
            if(code.host == request.getHeader("host") && code.account) {
                //System.out.println("Reauthenticating: "+code.account.email)
                springSecurityService.reauthenticate(code.account.email)
            }
            //System.out.println "Redirecting to: "+code.url+" OF "+request.getHeader("host")
            redirect url: "http://".concat(code.host).concat(code.url)
            securityCodeRepo.delete(code)
        }
        session.hostAuthToken = null
    }
}