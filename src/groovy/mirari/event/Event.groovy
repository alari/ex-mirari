package mirari.event

import org.apache.commons.collections.MapUtils

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
        type = EventType.byName((String) serializedEvent.type)
        params = serializedEvent.params
    }

    Map<String, Object> toMap() {
        [type: type.name, params: params]
    }

    Map<String, Object> getParams() {
        params
    }

    Event putParams(Map<String, Object> params) {
        merge(this.params, params)
        this
    }
    
    private void merge(Map base, final Map mix) {
        for(k in mix.keySet()) {
            if(base.containsKey(k)) {
                if(base[k] instanceof Map && mix[k] instanceof Map) {
                    merge((Map)base[k], (Map)mix[k])
                } else if(base[k] instanceof List) {
                    ((List)base[k]).addAll(mix[k])
                } else {
                    base.put(k, mix.get(k))
                }
            } else {
                base.put(k, mix.get(k))
            }
        }
    }

    String toString() {
        "Event(${type.name}){${params}};"
    }

    void fire() {
        EventMediator.instance.fire(this)
    }
}
