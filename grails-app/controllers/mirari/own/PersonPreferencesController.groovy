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

@Secured("ROLE_USER")
class PersonPreferencesController extends UtilController {

    static final Logger log = Logger.getLogger(this)
    
    def personPreferencesActService
    def avatarService
    Site.Dao siteDao

    def index() {
        [
                profile: currentProfile,
                account: currentAccount
        ]
    }

    def changeDisplayName(ChangeDisplayNameCommand command){
        alert personPreferencesActService.displayName(command, currentProfile)

        renderAlerts()

        render template: "changeDisplayName", model: [person: currentProfile, changeDisplayNameCommand: command]
    }

    def uploadAvatar() {
        if (request.post) {
            def f = request.getFile('avatar')
            ServiceResponse resp = avatarService.uploadSiteAvatar(f, currentProfile, siteDao)
            render(
                    [thumbnail: avatarService.getUrl(currentProfile, Avatar.LARGE),
                            alertCode: resp.alertCode].encodeAsJSON())
        }
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

class ChangeDisplayNameCommand {
    String displayName

    static constraints = {
        displayName minSize: 2, maxSize: 20, blank: true, nullable: true
    }
}