@Typed package mirari.dao

import com.google.code.morphia.Key
import mirari.model.Account
import mirari.model.Site
import mirari.repo.AccountRepo
import mirari.repo.SiteRepo
import org.springframework.beans.factory.annotation.Autowired
import ru.mirari.infra.mongo.BaseDao
import ru.mirari.infra.mongo.MorphiaDriver
import mirari.infra.SecurityService

/**
 * @author alari
 * @since 1/4/12 4:29 PM
 */
class AccountDao extends BaseDao<Account> implements AccountRepo {

    @Autowired private transient SiteRepo siteRepo;
    @Autowired private transient SecurityService securityService;

    @Autowired
    AccountDao(MorphiaDriver morphiaDriver) {
        super(morphiaDriver)
    }

    @Override
    Account getByEmail(String email) {
        return createQuery().filter("email", email).get();
    }

    @Override
    Account getByUsername(String username) {
        Account account = getByEmail(username);
        if (account == null) {
            Site profile = siteRepo.getByName(username);
            if (profile != null && profile.isProfileSite()) {
                account = profile.getAccount();
            }
        }
        return account;
    }

    @Override
    public Key<Account> save(Account account) {
        if (account.passwordChanged) {
            account.setPasswordHash(securityService.encodePassword(account.getPassword()));
        }
        return super.save(account);
    }

    @Override
    boolean emailExists(String email) {
        return createQuery().filter("email", email).countAll() > 0;
    }
}