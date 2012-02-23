package mirari.infra

import mirari.model.Account
import mirari.model.Site
import ru.mirari.infra.security.repo.AccountRepo
import org.springframework.web.context.request.RequestContextHolder

class SecurityService {

    static transactional = false

    def springSecurityService
    AccountRepo<Account> accountRepo

    Account getAccount() {
        loggedIn ? accountRepo.getById(id) : null
    }

    private Site get_site() {
        (Site)RequestContextHolder.currentRequestAttributes().getCurrentRequest()?._site
    }

    // TODO: cache user profiles in UserDetailsService
    Site getProfile() {
        Account account = account
        if (account) {
            if(_site?.account == account) {
                return _site
            }
            return account.mainProfile
        }
        null
    }

    boolean hasRole(String role) {
        if(!isLoggedIn()) return false;
        if(!role.startsWith("ROLE_")) {
            role = "ROLE_"+role.toUpperCase()
        }
        account.authorities*.authority.contains(role)
    }

    String getName() {
        loggedIn ? springSecurityService.principal.username : null
    }

    String getId() {
        loggedIn ? springSecurityService.principal.id : null
    }

    boolean isLoggedIn() {
        springSecurityService.isLoggedIn()
    }

    String encodePassword(String password) {
        springSecurityService.encodePassword(password)
    }
}
