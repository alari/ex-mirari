package ru.mirari.infra.security;

import com.google.code.morphia.Key;
import com.mongodb.WriteResult;
import org.bson.types.ObjectId;

/**
 * @author alari
 * @since 12/1/11 2:07 PM
 */
public interface AccountRepository<T extends UserAccount> {
    public T getByEmail(String email);
    public T getByUsername(String username);
    public T getById(String id);
    public T getById(ObjectId id);
    public boolean emailExists(String email);
    public Key<T> save(T o);
    public WriteResult delete(T o);
}
