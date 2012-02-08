@Typed package mirari.event

/**
 * @author alari
 * @since 2/7/12 11:03 PM
 */
abstract class EventListener {
    EventType getType() {
        null
    }

    boolean check(Event event) {
        true
    }

    abstract void handle(Event event)
}
