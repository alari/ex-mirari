package ru.mirari.infra.security;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Indexed;
import ru.mirari.infra.mongo.Domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author alari
 * @since 11/28/11 7:00 PM
 */
@Entity("security.account")
public class Account extends Domain implements UserAccount{
    private String password;
    @Indexed(unique = true)
    String email;

    boolean enabled;
    boolean accountExpired;

    transient public boolean passwordChanged;

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

    // Getters and setters...

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
