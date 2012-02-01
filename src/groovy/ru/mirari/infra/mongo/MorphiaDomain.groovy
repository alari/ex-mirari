@Typed package ru.mirari.infra.mongo;


import com.google.code.morphia.annotations.Id
import org.bson.types.ObjectId
import ru.mirari.infra.persistence.PersistentObject

/**
 * @author alari
 * @since 10/26/11 10:36 PM
 */
public abstract class MorphiaDomain implements PersistentObject {
    @Id
    private ObjectId id;

    public String getStringId() {
        return this.id == null ? "" : this.id.toString();
    }

    public boolean equals(Object rel) {
        return this.class.isInstance(rel) && getStringId().equals(((MorphiaDomain) rel).getStringId());
    }

    public boolean isPersisted() {
        return id != null;
    }
}
