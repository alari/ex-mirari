package mirari.repo;

import mirari.model.Page;
import mirari.model.Site;
import mirari.model.disqus.Comment;
import mirari.model.disqus.Reply;
import ru.mirari.infra.feed.FeedQuery;
import ru.mirari.infra.persistence.Repo;

/**
 * @author alari
 * @since 2/2/12 5:44 PM
 */
public interface ReplyRepo extends Repo<Reply> {
    FeedQuery<Reply> listByPage(Page page);

    FeedQuery<Reply> listByComment(Comment comment);

    FeedQuery<Reply> feed(Site site);

    void updatePageDiscovery(Page page);
}
