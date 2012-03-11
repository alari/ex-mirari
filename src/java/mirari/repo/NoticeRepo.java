package mirari.repo;

import mirari.model.Page;
import mirari.model.Site;
import mirari.model.digest.Notice;
import mirari.model.digest.NoticeReason;
import ru.mirari.infra.feed.FeedQuery;
import ru.mirari.infra.persistence.Repo;

/**
 * @author alari
 * @since 3/9/12 12:01 AM
 */
public interface NoticeRepo extends Repo<Notice>{
    FeedQuery<Notice> feed(Site owner);

    FeedQuery<Notice> feedUnwatched(Site owner);
        
    long countUnwatched(Site owner);
    
    void watch(Site owner, String id);

    void removeByPage(final Page page);

    void removeByReason(final NoticeReason reason);
}
