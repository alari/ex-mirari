package mirari.event

import com.google.code.morphia.annotations.CappedAt
import com.google.code.morphia.annotations.Entity
import com.google.code.morphia.annotations.Serialized
import ru.mirari.infra.mongo.MorphiaDomain

/**
 * @author alari
 * @since 2/27/12 8:55 PM
 */
@Entity(value = "event.queue", cap = @CappedAt(1048576L))
class EventDomain extends MorphiaDomain {
    @Serialized
    Map params

    EventType type

    boolean inProcess = false

    @Override
    protected boolean domainEventsEnabled() {
        false
    }

    void setEvent(Event e) {
        type = e.type
        params = e.params
    }

    Event getEvent() {
        new Event(type).putParams(params)
    }
}
