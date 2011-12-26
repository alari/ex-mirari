package ru.mirari.infra.security;

import com.google.code.morphia.Key;
import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Indexed;
import grails.plugins.springsecurity.SpringSecurityService;
import mirari.morphia.Site;
import mirari.morphia.site.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import ru.mirari.infra.mongo.BaseDao;
import ru.mirari.infra.mongo.Domain;
import ru.mirari.infra.mongo.MorphiaDriver;

import java.util.ArrayList;
import java.util.List;

/**
 * @author alari
 * @since 11/28/11 7:00 PM
 */
@Entity("security.account")
public class Account extends Domain{
    private String password;
    @Indexed(unique = true)
    String email;

    boolean enabled;
    boolean accountExpired;


    transient private boolean passwordChanged;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        passwordChanged = true;
    }

    public void setPasswordHash(String hash) {
        password = hash;
        passwordChanged = false;
    }

    @Embedded
    private List<Authority> authorities = new ArrayList<Authority>();

    boolean accountLocked;
    boolean passwordExpired;

    static public class Dao extends BaseDao<Account> implements AccountRepository {
        @Autowired private transient SpringSecurityService springSecurityService;
        @Autowired private transient Site.Dao siteDao;

        @Autowired
        Dao(MorphiaDriver morphiaDriver) {
            super(morphiaDriver);
        }

        public Account getByEmail(String email) {
            return createQuery().filter("email", email).get();
        }

        // not abstract
        public Account getByEmailOrProfileName(String emailOrName) {
            Account account = getByEmail(emailOrName);
            if(account == null) {
                Site profile = siteDao.getByName(emailOrName);
                if(profile != null && profile instanceof Profile) {
                    account = ((Profile)profile).getAccount();
                }
            }
            return account;
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isAccountExpired() {
        return accountExpired;
    }

    public void setAccountExpired(boolean accountExpired) {
        this.accountExpired = accountExpired;
    }

    public boolean isAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    public boolean isPasswordExpired() {
        return passwordExpired;
    }

    public void setPasswordExpired(boolean passwordExpired) {
        this.passwordExpired = passwordExpired;
    }

    public boolean isPasswordChanged() {
        return passwordChanged;
    }

    public void setPasswordChanged(boolean passwordChanged) {
        this.passwordChanged = passwordChanged;
    }

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
