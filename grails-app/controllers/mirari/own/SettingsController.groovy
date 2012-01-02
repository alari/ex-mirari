package mirari.own

import grails.plugins.springsecurity.Secured
import grails.plugins.springsecurity.SpringSecurityService

import mirari.UtilController
import mirari.morphia.Site
import mirari.validators.PasswordValidators
import org.springframework.beans.factory.annotation.Autowired

import org.apache.log4j.Logger
import mirari.morphia.site.Profile
import mirari.validators.NameValidators

@Secured("ROLE_USER")
class SettingsController extends UtilController {

    static final Logger log = Logger.getLogger(this)
    static final defaultAction = "index"
    
    def personPreferencesActService
    def avatarService
    Site.Dao siteDao
    Profile.Dao profileDao
    def siteLinkService

    def index() {
        [
                account: _account,
                profiles: profileDao.listByAccount(_account)
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
        alert personPreferencesActService.changePassword(command, _account)

        renderAlerts()

        render template: "changePassword", model: [chPwdCommand: command]
    }

    def createSite(CreateSiteCommand command) {
        Map model = [
                account: _account,
                profiles: profileDao.listByAccount(_account)
        ]
        if (request.post) {
            if (!command.hasErrors()) {
                if (profileDao.listByAccount(_account).iterator().size() > 2) {
                    errorCode = "Слишком много профилей. Создание нового блокировано"
                } else if (siteDao.nameExists(command.name)) {
                    errorCode = "Имя (адрес) сайта должно быть уникально"
                } else {
                    Profile profile = new Profile(name: command.name, displayName: command.displayName, account: _account)
                    profileDao.save(profile)
                    if (profile.id) {
                        redirect uri: siteLinkService.getUrl(profile, [action: "preferences"])
                        return
                    }
                }
            }
            model.put("command", command)
        } else {
            model.put("command", new CreateSiteCommand())
        }
        model
    }
}

class CreateSiteCommand {
    String name
    String displayName
    
    static constraints = {
        name NameValidators.CONSTRAINT_MATCHES
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