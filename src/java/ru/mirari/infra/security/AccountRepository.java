package ru.mirari.infra.security;

import com.google.code.morphia.Key;
import com.mongodb.WriteResult;
import org.bson.types.ObjectId;

/**
 * @author alari
 * @since 12/1/11 2:07 PM
 */
public interface AccountRepository {
    public Account getByEmail(String email);
    // Not abstract
    public Account getByEmailOrProfileName(String emailOrProfileName);
    public Account getById(String id);
    public Account getById(ObjectId id);
    public boolean emailExists(String email);
    public Key<Account> save(Account o);
    public WriteResult delete(Account o);
}
