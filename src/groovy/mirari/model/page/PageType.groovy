package mirari.model.page

import mirari.model.unit.content.internal.FeedContentStrategy

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

    static Map<String, PageType> byName = [:]

    static {
        for (PageType pt: values()) {
            byName.put(pt.name, pt)
        }
    }

    static PageType getByName(String name) {
        byName.get(name)
    }

    static Collection<PageType> baseValues() {
        values() - PAGE
    }

    PageType(String name) {
        this.name = name
    }

    String getName() {
        name
    }
    
    String getDefaultRenderStyle() {
        if(this == POST) {
            return FeedContentStrategy.STYLE_BLOG
        }
        if(this in [PHOTO, GRAPHICS, ART]) {
            return FeedContentStrategy.STYLE_SMALL
        }
        FeedContentStrategy.STYLE_WIDE
    }
    
    String getDefaultRenderNum() {
        "10"
    }
}