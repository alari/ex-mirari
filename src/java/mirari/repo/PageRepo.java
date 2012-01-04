package mirari.repo;

import ru.mirari.infra.feed.FeedQuery;
import mirari.ko.PageViewModel;
import mirari.model.Page;
import mirari.model.Site;
import ru.mirari.infra.mongo.Repo;

/**
 * @author alari
 * @since 1/4/12 4:10 PM
 */
public interface PageRepo extends Repo<Page>{
    public Page getByName(Site site, String name);

    public Page buildFor(PageViewModel pageViewModel, Page page);

    public Page buildFor(PageViewModel pageViewModel, Site site, Site owner);
    public Page buildFor(PageViewModel pageViewModel, Site site);

    public Iterable<Page> list() ;
    public Iterable<Page> list(int limit) ;

    public FeedQuery<Page> feed(Site site, boolean withDrafts);
    public FeedQuery<Page> feed(Site site);
}
