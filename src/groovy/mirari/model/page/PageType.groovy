package mirari.model.page

import mirari.model.unit.content.internal.FeedContentStrategy
import mirari.util.ApplicationContextHolder

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

    static private FeedContentStrategy _feedContentStrategy

    static {
        for (PageType pt: values()) {
            byName.put(pt.name, pt)
        }
    }

    private FeedContentStrategy getFeedContentStrategy() {
        if(_feedContentStrategy == null) {
            synchronized(PageType) {
                if(_feedContentStrategy == null) {
                    _feedContentStrategy = (FeedContentStrategy)ApplicationContextHolder.getBean("feedContentStrategy")
                }
            }
        }
        _feedContentStrategy
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
            return feedContentStrategy.STYLE_BLOG
        }
        if(this in [PHOTO, GRAPHICS, ART]) {
            return feedContentStrategy.STYLE_THUMBNAILS
        }
        feedContentStrategy.STYLE_WIDE
    }
    
    String getDefaultRenderNum() {
        "10"
    }
}