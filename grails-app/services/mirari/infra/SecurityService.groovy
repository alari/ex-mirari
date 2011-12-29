package mirari.infra

import ru.mirari.infra.security.AccountRepository
import mirari.morphia.site.Profile
import mirari.morphia.Account

class SecurityService {

    static transactional = false

    def springSecurityService
    AccountRepository<Account> accountRepository
    Profile.Dao profileDao

    Account getAccount(){
        loggedIn ? accountRepository.getById(id) : null
    }

    // TODO: cache user profiles in UserDetailsService
    Profile getProfile() {
        Account account = account
        if(account) {
            // TODO: return current site instance if it's linked to account
           return account.mainProfile
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
