package mirari.dao

import ru.mirari.infra.mongo.BaseDao
import mirari.model.site.PageFeed
import mirari.repo.PageFeedRepo
import ru.mirari.infra.mongo.MorphiaDriver
import org.springframework.beans.factory.annotation.Autowired
import mirari.model.Site
import com.google.code.morphia.query.Query
import mirari.model.page.PageType
import com.google.code.morphia.query.UpdateOperations

/**
 * @author alari
 * @since 2/21/12 9:20 PM
 */
class PageFeedDAO extends BaseDao<PageFeed> implements PageFeedRepo{
    @Autowired
    PageFeedDAO(MorphiaDriver morphiaDriver) {
        super(morphiaDriver)
    }

    @Override
    void createForSite(Site site) {
        for(PageType type : PageType.values()) {
            save new PageFeed(site: site, type: type, forceDisplay: site.isPortalSite())
        }
    }

    @Override
    Iterable<PageFeed> listAllBySite(final Site site) {
        getSiteQuery(site).fetch()
    }

    @Override
    Iterable<PageFeed> listDraftsBySite(final Site site) {
        getSiteQuery(site).filter("countAll >", 0).fetch()
    }

    @Override
    Iterable<PageFeed> listDisplayBySite(final Site site) {
        if(site.isPortalSite()) {
            getSiteQuery(site).filter("forceDisplay", true).fetch()
        } else {
            getSiteQuery(site).filter("countPubs >", 0).fetch()
        }
    }

    @Override
    void updateCounts(final List<Site> sites, final PageType type, Closure<UpdateOperations<PageFeed>> updateOps) {
        update(
                getCacheQuery(sites, type),
                updateOps( createUpdateOperations() )
        )
    }

    private Query<PageFeed> getCacheQuery(List<Site> sites, PageType type) {
        createQuery().filter("type", type).field("site").in(sites)
    }

    private Query<PageFeed> getSiteQuery(final Site site) {
        createQuery().filter("site", site).order("type")
    }
}
