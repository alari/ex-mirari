package mirari.model.digest

/**
 * @author alari
 * @since 3/8/12 11:46 PM
 */
public enum NoticeType {
    PAGE_COMMENT("page commented"),
    PAGE_REPLY("reply on page comment"),
    COMMENT_REPLY("reply to your comment");
    
    final private String title
    
    NoticeType(String n) {
        title = n
    }
    
    String toString() {
        "Notice(${title})"
    }
}
