package mirari.repo;

import mirari.model.Page;
import mirari.model.page.PageType;
import mirari.model.Site;
import mirari.model.Tag;
import ru.mirari.infra.feed.FeedQuery;
import ru.mirari.infra.persistence.Repo;

/**
 * @author alari
 * @since 1/4/12 4:10 PM
 */
public interface PageRepo extends Repo<Page>{
    public Page getByName(Site site, String name);

    public FeedQuery<Page> feed(Site site);
    public FeedQuery<Page> feed(Site site, PageType type);
    public FeedQuery<Page> feed(Tag tag);
    
    public FeedQuery<Page> drafts(Site site);
    public FeedQuery<Page> drafts(Site site, PageType type);
    public FeedQuery<Page> drafts(Tag tag);
}
