@Typed package mirari.model.strategy.content

import eu.medsea.mimeutil.MimeType
import mirari.util.ApplicationContextHolder

/**
 * @author alari
 * @since 1/6/12 7:50 PM
 */
public enum ContentPolicy {
    HTML("html"),
    TEXT("text"),
    IMAGE("image"),
    SOUND("sound"),
    YOUTUBE("youTube"),
    RUSSIARU("russiaRu"),
    RENDERBLOCK("renderInners");

    static private final Map<String, ContentPolicy> byName = [:]

    static {
        for (ContentPolicy cp in ContentPolicy.values()) byName.put(cp.name, cp)
    }

    static ContentPolicy getByName(String name) {
        byName.get(name)
    }

    static ContentPolicy findForMime(MimeType type) {
        values().find {it.getStrategy().isContentFileSupported(type)}
    }

    static ContentPolicy findForUrl(String url) {
        values().find {it.getStrategy().isUrlSupported(url)}
    }

    ContentStrategy getStrategy() {
        if (!strategy) {
            strategy = (ContentStrategy) ApplicationContextHolder.getBean(name.concat("ContentStrategy"))
        }
        strategy
    }

    private ContentStrategy strategy
    final String name

    private ContentPolicy(String name) {
        this.name = name
    }

    String toString() {
        "Content Policy \"${name}\""
    }
}