package mirari

import grails.gsp.PageRenderer
import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import ru.mirari.infra.mongo.Domain
import mirari.morphia.site.Profile
import ru.mirari.infra.security.Account

abstract class UtilController {
    def alertsService
    @Autowired PageRenderer groovyPageRenderer

    def securityService

    def Logger log = Logger.getLogger(this.getClass())

    protected Profile getCurrentProfile() {
        securityService.profile
    }

    protected Account getCurrentAccount() {
        securityService.account
    }

    protected String getCurrentAccountId() {
        securityService.id
    }

    protected boolean isLoggedIn() {
        securityService.loggedIn
    }

    protected void setErrorCode(String code) {
        alertsService.error flash, code
    }

    protected void setInfoCode(String code) {
        alertsService.info flash, code
    }

    protected void setSuccessCode(String code) {
        alertsService.success flash, code
    }

    protected void setWarningCode(String code) {
        alertsService.warning flash, code
    }

    protected boolean hasNoRight(boolean rightCheck, String errCode = "error.permission.denied",
                                 String redirectUri = null) {
        if (!rightCheck) {
            errorCode = errCode
            redirect uri: redirectUri ?: "/"
            return true
        }
        false
    }

    protected boolean isNotFound(List toCheck) {
        for (def o in toCheck) {
            if (isNotFound(o)) return true
        }
        false
    }

    protected boolean isNotFound(def toCheck) {
        if (!toCheck || (toCheck instanceof Domain && !toCheck.id)) {
            errorCode = "error.pageNotFound"
            redirect(uri: "/")
            return true
        }
        false
    }

    protected ServiceResponse alert(ServiceResponse resp) {
        alertsService.alert flash, resp
        resp
    }

    protected void renderAlerts() {
        render template: "/includes/alerts", model: [alerts: alertsService.getAlerts(flash)]
        alertsService.clean(flash)
    }

    protected void renderJson(ServiceResponse resp) {
        Map json = [
                srv: [
                        ok: resp.isOk()
                ],
                mdl: resp.model
        ]
        if (resp.redirect) {
            json.srv.redirect = createLink(resp.redirect)
        } else {
            alert resp
            json.srv.alerts = alertsService.getAlerts(flash).collect {[message:g.message(code: it.code, args: it.params),level:it.level.toString()]}
            alertsService.clean(flash)
        }

        render json.encodeAsJSON()
    }
}
