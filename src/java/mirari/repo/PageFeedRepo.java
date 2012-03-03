package mirari.repo;

import com.google.code.morphia.query.UpdateOperations;
import groovy.lang.Closure;
import mirari.model.Page;
import mirari.model.Site;
import mirari.model.page.PageType;
import mirari.model.site.PageFeed;
import ru.mirari.infra.persistence.Repo;

import java.util.List;

/**
 * @author alari
 * @since 2/21/12 9:19 PM
 */
public interface PageFeedRepo extends Repo<PageFeed>{
    Iterable<PageFeed> listAllBySite(Site site);
    Iterable<PageFeed> listDraftsBySite(Site site);
    Iterable<PageFeed> listDisplayBySite(Site site);

    PageFeed getByPage(Page page);
    void updateByPage(Page page);

    void updateCounts(final List<Site> sites, final PageType type, Closure<UpdateOperations<PageFeed>> updateOps);
}
