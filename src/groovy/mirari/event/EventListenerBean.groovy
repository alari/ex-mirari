@Typed package mirari.event

/**
 * @author alari
 * @since 2/7/12 11:03 PM
 */
abstract class EventListenerBean {
    abstract boolean check(EventType type)

    boolean check(Event event) {
        true
    }

    abstract void handle(Event event)
}
