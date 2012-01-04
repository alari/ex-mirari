package ru.mirari.infra.security.repo;

import ru.mirari.infra.mongo.Repo;
import ru.mirari.infra.security.UserAccount;

/**
 * @author alari
 * @since 12/1/11 2:07 PM
 */
public interface AccountRepo<T extends UserAccount> extends Repo<T> {
    public T getByEmail(String email);
    public T getByUsername(String username);
    public boolean emailExists(String email);
}
