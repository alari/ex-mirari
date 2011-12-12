package mirari.infra

import ru.mirari.infra.security.AccountRepository
import ru.mirari.infra.security.Account
import mirari.morphia.site.Profile

class SecurityService {

    static transactional = false

    def springSecurityService
    AccountRepository accountRepository
    Profile.Dao profileDao

    Account getAccount(){
        loggedIn ? accountRepository.getById(id) : null
    }

    // TODO: cache user profiles in UserDetailsService
    Profile getProfile() {
        Account account = account
        if(account) {
            return profileDao.listByAccount(account).iterator().next()
        }
        null
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
