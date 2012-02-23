@Typed package mirari.model.unit.content

/**
 * @author alari
 * @since 1/6/12 6:07 PM
 */
public enum ContentData {
    SOUND_TYPES("sound_types"),
    EXTERNAL_ID("external_id"),
    RENDER_STYLE("render_style"),

    FEED_SOURCE("feed_source"),
    FEED_LOCKED("feed_locked"),
    FEED_NUM("feed_num"),
    FEED_STYLE("feed_style"),
    FEED_ID("feed_id");

    public final String key;

    ContentData(String key) {
        this.key = key
    }

    String getFrom(ContentHolder unit) {
        unit.contentData.get(key)
    }

    Set<String> getSetFrom(ContentHolder unit) {
        Set<String> set = new HashSet<String>()
        String value = getFrom(unit)
        if (value) set.addAll(getFrom(unit).split(","));
        set
    }

    void putTo(ContentHolder unit, String value) {
        unit.contentData.put(key, value)
    }

    void putTo(ContentHolder unit, Collection<String> collection) {
        unit.contentData.put(key, collection.join(","))
    }
}