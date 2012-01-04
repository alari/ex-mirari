package mirari.infra

import ru.mirari.infra.security.repo.AccountRepo
import mirari.model.site.Profile
import mirari.model.Account
import mirari.repo.ProfileRepo

class SecurityService {

    static transactional = false

    def springSecurityService
    AccountRepo<Account> accountRepo
    ProfileRepo profileRepo

    Account getAccount(){
        loggedIn ? accountRepo.getById(id) : null
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
