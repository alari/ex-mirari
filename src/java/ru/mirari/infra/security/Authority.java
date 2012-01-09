package ru.mirari.infra.security;

import com.google.code.morphia.annotations.Embedded;
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

/**
 * @author alari
 * @since 11/28/11 7:00 PM
 */
@Embedded
public class Authority extends GrantedAuthorityImpl {
    public Authority() {
        super(SpringSecurityUtils.NO_ROLE);
    }

    public Authority(String role) {
        super(role);
    }
}
