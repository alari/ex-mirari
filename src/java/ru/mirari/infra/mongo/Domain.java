package ru.mirari.infra.mongo;

import com.google.code.morphia.annotations.Id;
import org.bson.types.ObjectId;

/**
 * @author alari
 * @since 10/26/11 10:36 PM
 */
public abstract class Domain {
    @Id
    private ObjectId id;

    public ObjectId getId() {
        return this.id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }
}
