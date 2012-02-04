package mirari.event

import org.apache.log4j.Logger
import ru.mirari.infra.persistence.PersistentObject

/**
 * @author alari
 * @since 2/3/12 1:15 PM
 */
@Singleton
class EventMediator {
    final private Logger log = Logger.getLogger(this.class)

    void fire(EventType type) {
        fire(new Event(type))
    }

    void fire(EventType type, PersistentObject object) {
        fire(new Event(type, object))
    }

    void fire(EventType type, Map<String, Object> params) {
        Event event = new Event(type)
        event.params.putAll(params)
        fire(event)
    }

    void fire(EventType type, PersistentObject object, Map<String, Object> params) {
        Event event = new Event(type, object)
        event.params.putAll(params)
        fire(event)
    }

    void fire(Event event) {
        log.info "Fired: " + event
    }
}
