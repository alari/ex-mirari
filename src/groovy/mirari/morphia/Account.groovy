package mirari.morphia

import ru.mirari.infra.security.AccountRepository
import ru.mirari.infra.mongo.BaseDao
import mirari.morphia.site.Profile
import org.springframework.beans.factory.annotation.Autowired
import ru.mirari.infra.mongo.MorphiaDriver
import com.google.code.morphia.Key
import grails.plugins.springsecurity.SpringSecurityService
import com.google.code.morphia.annotations.Entity

/**
 * @author alari
 * @since 12/27/11 3:03 PM
 */
@Entity("security.account")
class Account extends ru.mirari.infra.security.Account{

    static public class Dao extends BaseDao<Account> implements AccountRepository<Account>{

        @Autowired private transient Site.Dao siteDao;
        @Autowired private transient SpringSecurityService springSecurityService;

        @Autowired
        Dao(MorphiaDriver morphiaDriver) {
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
                Site profile = siteDao.getByName(username);
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
}
