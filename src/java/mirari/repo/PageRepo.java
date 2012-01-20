package mirari.repo;

import mirari.ko.PageViewModel;
import mirari.model.Page;
import mirari.model.PageType;
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

    public Iterable<Page> list() ;
    public Iterable<Page> list(int limit) ;

    public FeedQuery<Page> feed(Site site, boolean withDrafts);
    public FeedQuery<Page> feed(Site site);
    
    public FeedQuery<Page> feed(Site site, PageType type);
    
    public FeedQuery<Page> feed(Tag tag, boolean withDrafts);
    public FeedQuery<Page> feed(Tag tag);
}
