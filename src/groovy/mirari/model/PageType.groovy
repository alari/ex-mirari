package mirari.model

/**
 * @author alari
 * @since 1/15/12 7:44 PM
 */
public enum PageType {
    // text
    PROSE("prose"),
    POETRY("poetry"),
    ARTICLE("article"),
    POST("post"),
    
    // audio
    SOUND("sound"),
    MUSIC("music"),
    
    // visual
    PHOTO("photo"),
    GRAPHICS("graphics"),
    ART("art"),
    
    // default
    PAGE("page");
    
    private final String name
    
    static Map<String,PageType> byName = [:]

    static {
        for(PageType pt : values()) {
            byName.put(pt.name, pt)
        }
    }

    static PageType getByName(String name) {
        byName.get(name)
    }
    
    PageType(String name) {
        this.name = name
    }
    
    String getName() {
        name
    }
}