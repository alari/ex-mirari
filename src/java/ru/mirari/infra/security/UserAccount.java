package ru.mirari.infra.security;

import org.bson.types.ObjectId;

import java.util.List;

/**
 * @author alari
 * @since 12/27/11 3:07 PM
 */
public interface UserAccount {
    public String getPassword();
    public void setPassword(String password);

    public void setPasswordHash(String hash);

        public ObjectId getId();

        public boolean isEnabled();

        public void setEnabled(boolean enabled);

        public boolean isAccountExpired();

        public void setAccountExpired(boolean accountExpired);

        public boolean isAccountLocked();

        public void setAccountLocked(boolean accountLocked);

        public boolean isPasswordExpired();

        public void setPasswordExpired(boolean passwordExpired);

        public boolean isPasswordChanged();

        public void setPasswordChanged(boolean passwordChanged);

        public List<Authority> getAuthorities();

        public void setAuthorities(List<Authority> authorities);

        public String getEmail();

        public void setEmail(String email);
}
