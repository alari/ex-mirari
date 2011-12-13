package ru.mirari.infra.security;

import org.apache.log4j.Logger;
import org.codehaus.groovy.grails.plugins.springsecurity.GrailsUser;
import org.codehaus.groovy.grails.plugins.springsecurity.GrailsUserDetailsService;
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author alari
 * @since 11/28/11 7:25 PM
 */
public class UserDetailsService implements GrailsUserDetailsService {
    static private final Logger log = Logger.getLogger(UserDetailsService.class);

    @Autowired
    AccountRepository accountRepository;

    /**
     * Some Spring Security classes (e.g. RoleHierarchyVoter) expect at least one role, so
     * we give a user with no granted roles this one which gets past that restriction but
     * doesn't grant anything.
     */
    static final List<GrantedAuthority> NO_ROLES = Arrays.asList((GrantedAuthority)new GrantedAuthorityImpl(SpringSecurityUtils
            .NO_ROLE));

    public UserDetails loadUserByUsername(String email, boolean loadRoles) {
        if(log.isDebugEnabled()) {
            log.debug("Attempted user logon: ".concat(email));
        }

        Account account = accountRepository.getByEmailOrProfileName(email);

        if(account == null) {
            log.warn("User not found: ".concat(email));
            throw new UsernameNotFoundException("User not found", email);
        }

        if (log.isDebugEnabled()) {
            log.debug("User found: ".concat(email));
        }

        List<GrantedAuthority> roles = NO_ROLES;
        if (loadRoles) {
            if(account.getAuthorities().size() > 0) {
                roles = new ArrayList<GrantedAuthority>(account.getAuthorities().size());
                for(GrantedAuthority authority : account.getAuthorities()) {
                    roles.add(authority);
                }
            }
        }

        if (log.isDebugEnabled()) {
            log.debug("User roles: ".concat(roles.toString()));
        }

        return createUserDetails(account, roles);
    }

    public UserDetails loadUserByUsername(String username) {
        return loadUserByUsername(username, true);
    }

    protected UserDetails createUserDetails(Account account, Collection<GrantedAuthority> authorities) {
        return new GrailsUser(account.getEmail(), account.getPassword(), account.isEnabled(),
                !account.isAccountExpired(), !account.isPasswordExpired(),
                !account.isAccountLocked(), authorities, account.getId().toString());
    }
}