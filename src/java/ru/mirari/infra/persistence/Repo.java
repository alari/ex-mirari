package ru.mirari.infra.persistence;

import com.google.code.morphia.Key;
import com.mongodb.WriteResult;
import org.bson.types.ObjectId;

/**
 * @author alari
 * @since 1/4/12 4:05 PM
 */
public interface Repo<T> {
    public Object save(T o);

    public Object delete(T o);

    public T getById(String id);
}
