package mirari.infra

import mirari.morphia.space.subject.Person

class SecurityService {

    static transactional = false

    def springSecurityService
    Person.Dao personDao

    Person getPerson() {
        loggedIn ? personDao.getById(id) : null
    }

    String getName() {
        loggedIn ? springSecurityService.principal.username : null
    }

    String getId() {
        loggedIn ? springSecurityService.principal.id.toString() : null
    }

    boolean isLoggedIn() {
        springSecurityService.isLoggedIn()
    }

    String encodePassword(String password) {
        springSecurityService.encodePassword(password)
    }
}
