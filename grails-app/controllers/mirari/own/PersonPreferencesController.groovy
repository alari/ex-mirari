package mirari.own

import grails.plugins.springsecurity.Secured
import grails.plugins.springsecurity.SpringSecurityService
import mirari.ServiceResponse
import mirari.UtilController
import mirari.morphia.Space
import mirari.validators.PasswordValidators
import org.springframework.beans.factory.annotation.Autowired

@Secured("ROLE_USER")
class PersonPreferencesController extends UtilController {

    def personPreferencesActService
    def avatarService

    def index = {
        [
                person: currentPerson
        ]
    }

    def changeDisplayName = { ChangeDisplayNameCommand command ->
        alert personPreferencesActService.displayName(command, currentPerson)

        renderAlerts()

        render template: "changeDisplayName", model: [person: currentPerson, changeDisplayNameCommand: command]
    }

    def uploadAvatar = {
        if (request.post) {
            def f = request.getFile('avatar')
            ServiceResponse resp = avatarService.uploadSpaceAvatar(f, currentPerson)
            render([thumbnail: avatarService.getUrl(currentPerson, Space.IMAGE_AVA_LARGE), alertCode: resp.alertCode].encodeAsJSON
                    ())
        }
    }

    def changeEmail = {ChangeEmailCommand command ->
        alert personPreferencesActService.setEmail(session, command)

        renderAlerts()
    }

    def applyEmailChange = {String t ->
        alert personPreferencesActService.applyEmailChange(session, t)
        redirect action: "index"
    }

    def changePassword = {ChangePasswordCommand command ->
        alert personPreferencesActService.changePassword(command, currentPerson)

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