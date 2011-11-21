@Typed package mirari.ko

import grails.converters.JSON

/**
 * @author alari
 * @since 11/15/11 10:57 PM
 */
abstract class ViewModel {
    Map<String,?> toMap() {
        Map<String, ?> m = [:]
        def o
        for(String k : properties.keySet()) {
            if(k == "class" || k == "metaClass") continue;
            o = properties.get(k)
            if(o instanceof ViewModel) {
                m.put(k, o.toMap())
            } else {
                m.put(k, o)
            }
        }
        m
    }

    String toString() {
        toMap().toMapString()
    }
}
