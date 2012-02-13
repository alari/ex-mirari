@Typed package mirari.event

/**
 * @author alari
 * @since 2/7/12 11:03 PM
 */
abstract class EventListenerBean {
    abstract boolean filter(EventType type)

    boolean filter(Event event) {
        true
    }

    abstract void handle(Event event)
}
