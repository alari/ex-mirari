package ru.mirari.infra.mongo;

import com.google.code.morphia.dao.BasicDAO;
import org.bson.types.ObjectId;

/**
 * @author alari
 * @since 11/20/11 5:27 PM
 */
abstract public class BaseDao<T> extends BasicDAO<T, ObjectId> implements Repo<T>{
    protected BaseDao(MorphiaDriver morphiaDriver) {
        super(morphiaDriver.mongo, morphiaDriver.morphia, morphiaDriver.dbName);
    }

    public T getById(String id) {
        if (!ObjectId.isValid(id)) return null;
        return getById(new ObjectId(id));
    }

    public T getById(ObjectId id) {
        return get(id);
    }
}
