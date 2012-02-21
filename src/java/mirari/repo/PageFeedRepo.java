package mirari.repo;

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
    void createForSite(Site site);
    
    Iterable<PageFeed> listAllBySite(Site site);
    Iterable<PageFeed> listDraftsBySite(Site site);
    Iterable<PageFeed> listDisplayBySite(Site site);
    
    void countPlusPub(List<Site> sites, PageType type);
    void countMinusPub(List<Site> sites, PageType type);
    void countPlusDraft(List<Site> sites, PageType type);
    void countMinusDraft(List<Site> sites, PageType type);
}
