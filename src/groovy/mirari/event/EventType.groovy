package mirari.event

/**
 * @author alari
 * @since 2/3/12 1:25 PM
 */
public enum EventType {
    PAGE_PUBLISHED("page published"),
    PAGE_DRAFT_CHANGED("page draft changed"),
    PAGE_DELETED("page deleted"),
    PAGE_AVATAR_CHANGED("page avatar changed"),

    PAGE_PLACED_ON_SITES_CHANGED("page placed on sites changed"),

    SITE_AVATAR_CHANGED("site avatar changed"),

    DOMAIN_POST_PERSIST("domain post persist"),
    DOMAIN_PERSIST("domain created and saved at the first time"),

    EMAIL_SEND("email send"),

    FOLLOWER_NEW("new follower for site"),

    TEST("test");

    static private Map<String, EventType> byName = [:]

    static {
        values().each {EventType eventType -> byName.put(eventType.name, eventType)}
    }

    static EventType byName(String name) {
        byName.get(name) ?: TEST
    }

    private final String name

    EventType(String name) {
        this.name = name
    }

    String getName() {
        name
    }
}
