package mirari.act

import mirari.ServiceResponse
import mirari.morphia.space.subject.Person
import mirari.own.ChangeDisplayNameCommand
import mirari.own.ChangeEmailCommand
import mirari.own.ChangePasswordCommand

class PersonPreferencesActService {

    static transactional = false

    def mailSenderService
    def i18n
    Person.Dao personDao
    def securityService

    ServiceResponse setEmail(session, ChangeEmailCommand command) {
        if (!securityService.loggedIn) {
            return new ServiceResponse().error("personPreferences.changeEmail.notLoggedIn")
        }

        if (command.hasErrors()) {
            return new ServiceResponse().error("personPreferences.changeEmail.errors")
        }

        String email = command.email

        if (email == securityService.person.email) {
            return new ServiceResponse().warning("personPreferences.changeEmail.oldEmailInput")
        }

        if (personDao.emailExists(email)) {
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

        Person person = securityService.person
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
            if (currentPerson.password != securityService.encodePassword(command.oldPassword)) {
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

    ServiceResponse displayName(ChangeDisplayNameCommand command, Person currentPerson) {
        if (command.hasErrors()) {
            return new ServiceResponse().error("personPreferences.changeDisplayName.error")
        }
        currentPerson.displayName = command.displayName
        personDao.save(currentPerson)

        if (currentPerson.displayName == command.displayName) {
            new ServiceResponse().success("personPreferences.changeDisplayName.success")
        } else {
            new ServiceResponse().error("personPreferences.changeDisplayName.error")
        }
    }
}
