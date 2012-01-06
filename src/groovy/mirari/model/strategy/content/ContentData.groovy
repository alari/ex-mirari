@Typed package mirari.model.strategy.content

import mirari.model.Unit

/**
 * @author alari
 * @since 1/6/12 6:07 PM
 */
public enum ContentData {
    SOUND_TYPES("sound_types"),
    EXTERNAL_ID("external_id");
    
    public final String key;
    
    ContentData(String key) {
        this.key = key
    }
    
    String getFrom(Unit unit) {
        unit.contentData.get(key)
    }
    
    Set<String> getSetFrom(Unit unit) {
        Set<String> set = new HashSet<String>()
        set.addAll(getFrom(unit).split(","))
        set
    }

    void putTo(Unit unit, String value) {
        unit.contentData.put(key, value)
    }

    void putTo(Unit unit, Collection<String> collection) {
        unit.contentData.put(key, collection.join(","))
    }
}