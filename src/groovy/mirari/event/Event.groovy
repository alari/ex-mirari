package mirari.event

/**
 * @author alari
 * @since 2/3/12 1:25 PM
 */
class Event {
    final public EventType type
    private Map<String, Object> params = [:]

    Event(EventType type) {
        this.type = type
    }
    
    Event(String type) {
        this.type = EventType.byName(type)
    }
    
    Event(Map serializedEvent) {
        type = EventType.byName((String)serializedEvent.type)
        params = serializedEvent.params
    }
    
    Map<String,Object> toMap() {
        [type: type.name, params: params]
    }

    Map<String, Object> getParams() {
        params
    }
    
    Event putParams(Map<String,Object> params) {
        this.params.putAll(params)
        this
    }

    String toString() {
        "Event(${type.name}){${params}};"
    }
    
    void fire() {
        EventMediator.instance.fire(this)
    }
}
