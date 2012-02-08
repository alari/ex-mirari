package mirari.event

import org.apache.log4j.Logger

/**
 * @author alari
 * @since 2/7/12 11:06 PM
 */
class LoggingEventListener extends EventListener{
    private final Logger log = Logger.getLogger(this.class)
    
    @Override
    void handle(Event event) {
        println("Receieved event: ".concat(event.toString()))
    }
}
