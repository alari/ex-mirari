@Typed package mirari.model.unit.content

import mirari.util.ApplicationContextHolder
import ru.mirari.infra.file.FileInfo
import mirari.model.unit.inners.InnersPolicy

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
    RENDERBLOCK("renderInners"),
    FEED("feed"),
    PAGEREFERENCE("pageReference"),
    COMPOUND("compound", InnersPolicy.COMPOUND);

    static private final Map<String, ContentPolicy> byName = [:]

    static {
        for (ContentPolicy cp in ContentPolicy.values()) byName.put(cp.name, cp)
    }

    static ContentPolicy getByName(String name) {
        byName.get(name)
    }

    static ContentPolicy findForFileInfo(FileInfo info) {
        values().find {it.getStrategy().isContentFileSupported(info)}
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
    final InnersPolicy innersPolicy

    private ContentPolicy(String name) {
        this.name = name
        innersPolicy = InnersPolicy.ANY
    }

    private ContentPolicy(String name, InnersPolicy policy) {
        this.name = name
        innersPolicy = policy
    }

    String toString() {
        "Content Policy \"${name}\""
    }
}