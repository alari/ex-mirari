package mirari.model.digest

import mirari.model.disqus.Comment
import mirari.model.disqus.Reply

/**
 * @author alari
 * @since 3/8/12 11:46 PM
 */
public enum NoticeType {
    PAGE_COMMENT(
            "page_comment",
            Comment
    ),
    PAGE_REPLY(
            "page_reply",
            Reply
    ),
    COMMENT_REPLY(
            "comment_reply",
            Reply
    );
    
    final public String name
    final public Class<? extends NoticeReason> reasonType 
    
    NoticeType(String name, Class<? extends NoticeReason> reasonType) {
        this.name = name
        this.reasonType = reasonType
    }

    String toString() {
        "Notice(${name})"
    }
}
