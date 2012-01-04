@Typed package mirari.dao

import ru.mirari.infra.mongo.BaseDao
import mirari.repo.AccountRepo
import org.springframework.beans.factory.annotation.Autowired
import mirari.model.Site
import grails.plugins.springsecurity.SpringSecurityService
import ru.mirari.infra.mongo.MorphiaDriver
import mirari.model.site.Profile
import com.google.code.morphia.Key
import mirari.model.Account
import mirari.repo.SiteRepo

/**
 * @author alari
 * @since 1/4/12 4:29 PM
 */
class AccountDao extends BaseDao<Account> implements AccountRepo{

    @Autowired private transient SiteRepo siteRepo;
    @Autowired private transient SpringSecurityService springSecurityService;

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
        if(account == null) {
            Site profile = siteRepo.getByName(username);
            if(profile != null && profile instanceof Profile) {
                account = ((Profile)profile).getAccount();
            }
        }
        return account;
    }

    @Override
    public Key<Account> save(Account account) {
        if (account.passwordChanged) {
            account.setPasswordHash(springSecurityService.encodePassword(account.getPassword(), null));
        }
        return new Key<Account>(Account.class, super.save(account).getId());
    }

    @Override
    boolean emailExists(String email) {
        return createQuery().filter("email", email).countAll() > 0;
    }
}