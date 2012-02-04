package mirari.event

import ru.mirari.infra.persistence.PersistentObject

/**
 * @author alari
 * @since 2/3/12 1:25 PM
 */
class Event{
    final public EventType type
    private Map<String,Object> params = [:]
    private PersistentObject object

    Event(EventType type) {
        this.type = type
    }

    Event(EventType type, final PersistentObject object) {
        this.type = type
        this.object = object
    }

    void setObject(final PersistentObject object) {
        this.object = object
    }

    String getStringId() {
        this.object?.stringId
    }

    String getObjectClassName() {
        this.object?.class?.canonicalName
    }

    Class<PersistentObject> getObjectClass() {
        this.object?.class
    }

    PersistentObject getObject() {
        object
    }
    
    Map<String,Object> getParams() {
        params
    }
    
    String toString() {
        "Event{${type}, ${objectClassName}, ${stringId}}"
    }
}
