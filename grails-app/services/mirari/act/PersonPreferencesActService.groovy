package mirari.act

import mirari.ServiceResponse
import mirari.morphia.space.subject.Person
import mirari.own.ChangeEmailCommand
import mirari.own.ChangePasswordCommand

class PersonPreferencesActService {

    static transactional = false

    def mailSenderService
    def i18n
    def springSecurityService
    Person.Dao personDao

    ServiceResponse setEmail(session, ChangeEmailCommand command) {
        if (!springSecurityService.isLoggedIn()) {
            return new ServiceResponse().error("personPreferences.changeEmail.notLoggedIn")
        }

        if (command.hasErrors()) {
            return new ServiceResponse().error("personPreferences.changeEmail.errors")
        }

        String email = command.email

        session.changeEmail = email
        session.changeEmailToken = UUID.randomUUID().toString().replaceAll('-', '')

        mailSenderService.putMessage(
                to: email,
                subject: i18n."personPreferences.changeEmail.mailTitle",
                model: [username: springSecurityService.principal?.username, token: session.changeEmailToken],
                view: "/mail-messages/changeEmail"
        )

        new ServiceResponse().success("personPreferences.changeEmail.checkEmail")
    }

    ServiceResponse applyEmailChange(session, String token) {
        ServiceResponse resp = new ServiceResponse()
        if (!springSecurityService.isLoggedIn()) {
            return resp.error("personPreferences.changeEmail.notLoggedIn")
        }
        if (!token || token != session.changeEmailToken) {
            return resp.error("personPreferences.changeEmail.wrongToken")
        }

        String email = session.changeEmail

        Person person = personDao.getById(springSecurityService.principal.id)
        if (!person) {
            return resp.error("personPreferences.changeEmail.personNotFound")
        }
        person.email = email
        personDao.save(person)
        session.changeEmail = null
        session.changeEmailToken = null

        resp.success("personPreferences.changeEmail.success")
    }

    ServiceResponse changePassword(ChangePasswordCommand command, Person currentPerson) {
        ServiceResponse resp = new ServiceResponse()
        if (command.oldPassword) {
            if (currentPerson.password != springSecurityService.encodePassword(command.oldPassword)) {
                return resp.warning("personPreferences.changePassword.incorrect")
            }
            if (!command.hasErrors()) {
                Person person = currentPerson
                person.password = command.password
                personDao.save(person)
                return resp.success("personPreferences.changePassword.success")
            }
        }
        resp
    }
}
