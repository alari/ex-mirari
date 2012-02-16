package mirari.event

import groovyx.gpars.GParsPool
import org.apache.log4j.Logger

/**
 * @author alari
 * @since 2/3/12 1:15 PM
 */
@Singleton
class EventMediator {
    static void fireStatic(String type, Map<String, Object> params) {

    }

    final private Logger log = Logger.getLogger(this.class)

    List<EventListenerBean> listeners = []

    void fire(EventType type) {
        fire(new Event(type))
    }

    void fire(String type) {
        fire new Event(type)
    }

    void fire(String type, Map<String, Object> params) {
        Event event = new Event(type)
        event.params.putAll(params)
        fire(event)
    }

    void fire(EventType type, Map<String, Object> params) {
        Event event = new Event(type)
        event.params.putAll(params)
        fire(event)
    }

    void fire(Event event) {
        // TODO: put an event to a queue instead
        applyHandlers(event)
    }

    // TODO: release events from a queue
    void applyHandlers(Event event) {
        GParsPool.withPool {
            listeners.eachParallel { EventListenerBean listener ->
                if (listener.filter(event.type) && listener.filter(event)) {
                    try {
                        listener.handle(event)
                    } catch (Exception e) {
                        log.error(e)
                        // try once more
                    }
                }
            }
        }
    }
}
