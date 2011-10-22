package mirari

import mirari.morphia.subject.Person
import mirari.morphia.subject.PersonDAO

class PersonPreferencesService {

  static transactional = false

  def mailSenderService
  def i18n
  def springSecurityService
  PersonDAO personDao

  ServiceResponse setEmail(session, String email) {
    if(!springSecurityService.isLoggedIn()) {
      return new ServiceResponse().error("personPreferences.changeEmail.notLoggedIn")
    }

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
    if(!springSecurityService.isLoggedIn()) {
      return resp.error("personPreferences.changeEmail.notLoggedIn")
    }
    if(!token || token != session.changeEmailToken) {
      return resp.error("personPreferences.changeEmail.wrongToken")
    }

    String email = session.changeEmail

    Person person = personDao.getById(springSecurityService.principal.id)
    if(!person) {
      return resp.error("personPreferences.changeEmail.personNotFound")
    }
    person.email = email
    personDao.save(person)
    session.changeEmail = null
    session.changeEmailToken = null

    resp.success("personPreferences.changeEmail.success")
  }
}
