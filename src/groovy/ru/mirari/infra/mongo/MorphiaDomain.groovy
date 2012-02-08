@Typed package ru.mirari.infra.mongo;


import com.google.code.morphia.annotations.Id
import org.bson.types.ObjectId
import ru.mirari.infra.persistence.PersistentObject
import mirari.event.Event
import com.google.code.morphia.annotations.PostPersist
import mirari.event.EventType
import com.google.code.morphia.annotations.Transient

/**
 * @author alari
 * @since 10/26/11 10:36 PM
 */
public abstract class MorphiaDomain implements PersistentObject {
    @Id
    private ObjectId id;

    @Transient
    transient private Map<EventType,Event> postPersistEvents = [:]

    public String getStringId() {
        return this.id == null ? "" : this.id.toString();
    }

    public boolean equals(Object rel) {
        return this.class.isInstance(rel) && getStringId().equals(((MorphiaDomain) rel).getStringId());
    }

    public boolean isPersisted() {
        return id != null;
    }

    /*      Events      */

    final Event firePostPersist(Event e) {
        postPersistEvents.put(e.type, e)
        e
    }

    final Event firePostPersist(EventType e) {
        firePostPersist(new Event(e))
    }

    final Event firePostPersist(EventType e, Map<String, Object> params) {
        firePostPersist(e).putParams(params)
    }

    @PostPersist
    final private void postPersistEvents() {
        for(Event e in postPersistEvents.values()) {
            System.err.println "Fire: "+e
            e.params.put("_id", stringId)
            e.fire()
        }
        postPersistEvents = [:]
    }
}
