package ru.mirari.infra.security.dao;

import com.google.code.morphia.Key;
import grails.plugins.springsecurity.SpringSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import ru.mirari.infra.mongo.BaseDao;
import ru.mirari.infra.mongo.MorphiaDriver;
import ru.mirari.infra.security.Account;
import ru.mirari.infra.security.repo.AccountRepo;

/**
 * @author alari
 * @since 1/4/12 4:32 PM
 */
public class AccountDao extends BaseDao<Account> implements AccountRepo<Account> {
    @Autowired
    private transient SpringSecurityService springSecurityService;

    @Autowired
    AccountDao(MorphiaDriver morphiaDriver) {
        super(morphiaDriver);
    }

    public Account getByEmail(String email) {
        return createQuery().filter("email", email).get();
    }

    public Account getByUsername(String username) {
        return getByEmail(username);
    }

    public Key<Account> save(Account account) {
        if (account.passwordChanged) {
            account.setPasswordHash(springSecurityService.encodePassword(account.getPassword(), null));
        }
        return new Key<Account>(Account.class, super.save(account).getId());
    }

    public boolean emailExists(String email) {
        return createQuery().filter("email", email).countAll() > 0;
    }
}
