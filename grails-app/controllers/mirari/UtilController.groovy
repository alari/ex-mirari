package mirari

import grails.gsp.PageRenderer
import mirari.morphia.space.subject.Person
import org.springframework.beans.factory.annotation.Autowired

abstract class UtilController {
    def springSecurityService
    def alertsService
    @Autowired Person.Dao personDao
    @Autowired PageRenderer groovyPageRenderer

    protected Person getCurrentPerson() {
        personDao.getById(springSecurityService.principal?.id?.toString())
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

    protected boolean hasNoRight(boolean rightCheck, String errCode = "permission.denied", String redirectUri = null) {
        if (!rightCheck) {
            errorCode = errCode
            redirect uri: redirectUri ?: "/"
            return true
        }
        false
    }

    protected void alert(ServiceResponse resp) {
        alertsService.alert flash, resp
    }

    protected void renderAlerts() {
        render template: "/includes/alerts", model: [alerts: alertsService.getAlerts(flash)]
        alertsService.clean(flash)
    }

    protected renderJson(ServiceResponse resp) {
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
            json.srv.alerts = groovyPageRenderer.render(template: "/includes/alerts", model: [alerts: alertsService.getAlerts(flash)])
            alertsService.clean(flash)
        }

        render json.encodeAsJSON()
    }
}
