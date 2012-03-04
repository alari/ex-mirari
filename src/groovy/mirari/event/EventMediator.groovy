package mirari.event

import groovyx.gpars.GParsPool
import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import java.util.concurrent.TimeUnit
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService

/**
 * @author alari
 * @since 2/3/12 1:15 PM
 */
@Singleton
class EventMediator {
    final private Logger log = Logger.getLogger(this.class)

    List<EventListenerBean> listeners = []

    @Autowired EventRepo eventRepo

    static private final long period = 750L

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
        eventRepo.put(event)
    }

    private void launch() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(3);
        executor.scheduleAtFixedRate(new Worker(eventRepo), 0L, period, TimeUnit.MILLISECONDS);
    }

    void applyHandlers(Event event) {
        GParsPool.withPool {
            listeners.eachParallel { EventListenerBean listener ->
                if (listener.filter(event.type) && listener.filter(event)) {
                    log.info "Applying to ${event.type}: " + listener.class.canonicalName
                    try {
                        listener.handle(event)
                    } catch (Exception e) {
                        log.error("Error while handling event listener for ${event}", e)
                        // TODO: try once more
                    }
                }
            }
        }
    }

    private static class Worker extends TimerTask {

        private EventRepo eventRepo

        Worker(EventRepo repo) {
            this.eventRepo = repo
        }

        @Override
        void run() {
            for (EventDomain eventDomain in eventRepo.getToProcess()) {
                try {
                    handle(eventDomain.event)
                } catch (Exception e) {
                    println e
                }
            }
        }

        void handle(Event event) {
            EventMediator.instance.applyHandlers(event)
        }
    }
}
