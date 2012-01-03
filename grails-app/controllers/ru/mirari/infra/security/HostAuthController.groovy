package ru.mirari.infra.security

import mirari.morphia.Site
import mirari.UtilController

class HostAuthController extends UtilController{
    def springSecurityService
    def siteService
    SecurityCode.Dao securityCodeRepository

    def ask(String token) {
        SecurityCode code = securityCodeRepository.getByToken(token)
        if(code && code.host != request.getHeader("host")) {
            Site ref = siteService.getByHost(code.host)
            if(ref) {
                if(springSecurityService.isLoggedIn()) {
                    code.account = securityService.account
                    securityCodeRepository.save(code)
                }
                redirect url: ref.getUrl(controller: controllerName, action: "reply")
            }
        }
    }

    def reply() {
        //System.out.println "Before for /-auth-host-reply, token: "+session.hostAuthToken

        SecurityCode code = securityCodeRepository.getByToken(session.hostAuthToken)
        if(code) {
            // validate
            if(code.host == request.getHeader("host") && code.account) {
                //System.out.println("Reauthenticating: "+code.account.email)
                springSecurityService.reauthenticate(code.account.email)
            }
            //System.out.println "Redirecting to: "+code.url+" OF "+request.getHeader("host")
            redirect url: "http://".concat(code.host).concat(code.url)
            securityCodeRepository.delete(code)
        }
        session.hostAuthToken = null
    }
}
