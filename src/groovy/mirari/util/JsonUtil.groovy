package mirari.util

import org.codehaus.jackson.map.ObjectMapper

/**
 * @author alari
 * @since 2/16/12 4:20 PM
 */
class JsonUtil {
    public final static ObjectMapper mapper = new ObjectMapper()

    static String objToString(obj) {
        mapper.writeValueAsString(obj)
    }

    static <T> T stringToObj(String string, Class<T> viewModelClass) {
        mapper.readValue(string, viewModelClass)
    }
}
