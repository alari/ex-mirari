package mirari.own

import grails.plugins.springsecurity.Secured
import grails.plugins.springsecurity.SpringSecurityService
import mirari.ServiceResponse
import mirari.UtilController
import mirari.morphia.Site
import mirari.validators.PasswordValidators
import org.springframework.beans.factory.annotation.Autowired
import mirari.morphia.Avatar
import org.apache.log4j.Logger
import mirari.morphia.site.Profile

@Secured("ROLE_USER")
class SettingsController extends UtilController {

    static final Logger log = Logger.getLogger(this)
    static final defaultAction = "index"
    
    def personPreferencesActService
    def avatarService
    Site.Dao siteDao
    Profile.Dao profileDao

    def index() {
        [
                account: currentAccount,
                profiles: profileDao.listByAccount(currentAccount)
        ]
    }


    def changeEmail(ChangeEmailCommand command){
        alert personPreferencesActService.setEmail(session, command)

        renderAlerts()
    }

    def applyEmailChange(String t){
        alert personPreferencesActService.applyEmailChange(session, t)
        redirect action: "index"
    }

    def changePassword(ChangePasswordCommand command){
        alert personPreferencesActService.changePassword(command, currentAccount)

        renderAlerts()

        render template: "changePassword", model: [chPwdCommand: command]
    }

    def createSite() {
        [
                account: currentAccount,
                profiles: profileDao.listByAccount(currentAccount)
        ]
    }
}

class ChangeEmailCommand {
    String email

    static constraints = {
        email email: true, blank: false
    }
}

class ChangePasswordCommand {
    String name
    String oldPassword
    String password
    String password2

    @Autowired
    SpringSecurityService springSecurityService

    static constraints = {
        name blank: false
        oldPassword blank: false
        password blank: false, minSize: 7, maxSize: 64, validator: PasswordValidators.passwordValidator
        password2 validator: PasswordValidators.password2Validator
    }
}