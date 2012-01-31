package ru.mirari.infra.security

import grails.plugins.springsecurity.Secured
import mirari.UtilController
import mirari.repo.SiteRepo
import mirari.util.ServiceResponse
import mirari.util.validators.NameValidators
import mirari.util.validators.PasswordValidators
import org.springframework.beans.factory.annotation.Autowired
import ru.mirari.infra.security.repo.AccountRepo

@Secured("IS_AUTHENTICATED_ANONYMOUSLY")
class RegisterController extends UtilController {

    static defaultAction = 'index'

    def registrationActService

    def index(RegisterCommand command) {
        Map model
        if (request.post) {
            ServiceResponse resp = registrationActService.handleRegistration(command, _mainPortal)
            alertsService.alert(flash, resp)
            model = resp.model
            model.put("command", command)
            return model
        } else {
            return [command: new RegisterCommand()]
        }
    }

    def verifyRegistration() {
        String token = params.t
        ServiceResponse result = registrationActService.verifyRegistration(token)
        alertsService.alert(flash, result)

        redirect result.redirect
    }

    def forgotPassword() {

        if (!request.post) {
            // show the form
            render view: "/register/forgotPassword"
            return
        }

        ServiceResponse result = registrationActService.handleForgotPassword(params.name, _mainPortal)
        alertsService.alert(flash, result)

        render view: "/register/forgotPassword", model: result.model
    }

    def resetPassword(ResetPasswordCommand command) {

        String token = params.t

        ServiceResponse result = registrationActService.handleResetPassword(token, command, request.method)
        alertsService.alert(flash, result)

        if (!result.ok) {
            if (result.redirect) {
                redirect result.redirect
            } else {
                render view: "/register/resetPassword", model: result.model
            }
        } else {
            redirect result.redirect
        }
    }
}

/**
 * @author Dmitry Kurinskiy
 * @since 18.08.11 23:02
 */
class RegisterCommand {
    @Autowired AccountRepo accountRepo
    @Autowired SiteRepo siteRepo

    String email
    String password
    String password2

    String name
    String displayName

    static constraints = {
        name blank: false, validator: { value, command ->
            if (value) {
                if (command.siteRepo.nameExists(value)) {
                    return 'registerCommand.name.unique'
                }
                if (!((String) value).matches(NameValidators.MATCHER)) {
                    return "registerCommand.name.invalid"
                }
            }
        }
        email blank: false, email: true, validator: { String email, command ->
            if (command.accountRepo.emailExists(command.email)) {
                return 'registerCommand.email.notUnique'
            }
        }
        password blank: false, minSize: 7, maxSize: 64, validator: PasswordValidators.passwordValidator
        password2 validator: PasswordValidators.password2Validator
    }
}
