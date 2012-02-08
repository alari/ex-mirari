package mirari.event

import org.apache.log4j.Logger
import groovyx.gpars.GParsPool

/**
 * @author alari
 * @since 2/3/12 1:15 PM
 */
@Singleton
class EventMediator {
    final private Logger log = Logger.getLogger(this.class)

    List<EventListener> listeners = []
    
    void fire(EventType type) {
        fire(new Event(type))
    }
    
    void fire(String type) {
        fire new Event(type)
    }
    
    void fire(String type, Map<String,Object> params) {
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
        GParsPool.withPool {o->
            GParsPool.executeAsync {
                applyHandlers(event)
            }
        }
    }

    // TODO: release events from a queue
    void applyHandlers(Event event) {
        GParsPool.withPool {
            listeners.eachParallel { EventListener listener->
                if( (!listener.type || listener.type == event.type) && listener.check(event) ) {
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
