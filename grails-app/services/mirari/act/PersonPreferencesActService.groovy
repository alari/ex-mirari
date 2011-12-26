package mirari.act

import mirari.ServiceResponse
import mirari.own.ChangeEmailCommand
import mirari.own.ChangePasswordCommand
import ru.mirari.infra.security.Account
import mirari.morphia.site.Profile

class PersonPreferencesActService {

    static transactional = false

    def mailSenderService
    def i18n
    def securityService
    Account.Dao accountRepository
    Profile.Dao profileDao

    ServiceResponse setEmail(session, ChangeEmailCommand command) {
        if (!securityService.loggedIn) {
            return new ServiceResponse().error("personPreferences.changeEmail.notLoggedIn")
        }

        if (command.hasErrors()) {
            return new ServiceResponse().error("personPreferences.changeEmail.errors")
        }

        String email = command.email

        if (email == securityService.account.email) {
            return new ServiceResponse().warning("personPreferences.changeEmail.oldEmailInput")
        }

        if (accountRepository.emailExists(email)) {
            return new ServiceResponse().warning("personPreferences.changeEmail.notUnique")
        }

        session.changeEmail = email
        session.changeEmailToken = UUID.randomUUID().toString().replaceAll('-', '')

        mailSenderService.putMessage(
                to: email,
                subject: i18n."personPreferences.changeEmail.mailTitle",
                model: [username: securityService.name,
                        token: session.changeEmailToken],
                view: "/mail-messages/changeEmail"
        )

        new ServiceResponse().success("personPreferences.changeEmail.checkEmail")
    }

    ServiceResponse applyEmailChange(session, String token) {
        ServiceResponse resp = new ServiceResponse()
        if (!securityService.loggedIn) {
            return resp.error("personPreferences.changeEmail.notLoggedIn")
        }
        if (!token || token != session.changeEmailToken) {
            return resp.error("personPreferences.changeEmail.wrongToken")
        }

        String email = session.changeEmail

        Account account = securityService.account
        if (!account) {
            return resp.error("personPreferences.changeEmail.personNotFound")
        }
        if(accountRepository.emailExists(email)) {
            return resp.error("personPreferences.changeEmail.emailExists")
        }
        account.email = email
        accountRepository.save(account)

        session.changeEmail = null
        session.changeEmailToken = null

        resp.success("personPreferences.changeEmail.success")
    }

    ServiceResponse changePassword(ChangePasswordCommand command, Account account) {
        ServiceResponse resp = new ServiceResponse()
        if (command.oldPassword) {
            if (account.password != securityService.encodePassword(command.oldPassword)) {
                return resp.warning("personPreferences.changePassword.incorrect")
            }
            if (!command.hasErrors()) {
                account.password = command.password
                accountRepository.save(account)
                return resp.success("personPreferences.changePassword.success")
            }
        }
        resp
    }
}
