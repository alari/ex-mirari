package ru.mirari.infra.persistence;

import com.google.code.morphia.Key;
import com.mongodb.WriteResult;

/**
 * @author alari
 * @since 1/4/12 4:05 PM
 */
public interface Repo<T> {
    public Key<T> save(T o);

    public WriteResult delete(T o);

    public T getById(String id);
}
