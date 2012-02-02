package mirari.repo;

import mirari.model.Page;
import mirari.model.disqus.Comment;
import ru.mirari.infra.feed.FeedQuery;
import ru.mirari.infra.persistence.Repo;

/**
 * @author alari
 * @since 2/2/12 3:42 PM
 */
public interface CommentRepo extends Repo<Comment> {
    FeedQuery<Comment> listByPage(Page page);
}
