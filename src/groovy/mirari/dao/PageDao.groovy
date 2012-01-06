@Typed package mirari.dao

import com.google.code.morphia.Key
import com.google.code.morphia.query.Query
import com.mongodb.WriteResult
import mirari.ko.PageViewModel
import mirari.model.Page
import mirari.model.Site
import mirari.model.Unit
import mirari.repo.PageRepo
import mirari.repo.UnitRepo
import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import ru.mirari.infra.feed.FeedQuery
import ru.mirari.infra.mongo.BaseDao
import ru.mirari.infra.mongo.MorphiaDriver

/**
 * @author alari
 * @since 1/4/12 4:38 PM
 */
class PageDao extends BaseDao<Page> implements PageRepo{
    @Autowired private UnitRepo unitRepo
    static final private Logger log = Logger.getLogger(this)

    @Autowired
    PageDao(MorphiaDriver morphiaDriver) {
        super(morphiaDriver)
    }

    Page getByName(Site site, String name) {
        createQuery().filter("site", site).filter("name", name.toLowerCase()).get()
    }

    Page buildFor(PageViewModel pageViewModel, Page page) {
        pageViewModel.assignTo(page)

        Map<String, Unit> restInners = new HashMap<String, Unit>()
        page.setInners(pageViewModel, restInners)

        // TODO: the rest of units must be deleted or modified if they have anchors
        println "The rest of units: ${restInners}"

        for(Unit u in restInners.values()) {
            unitRepo.delete(u)
        }

        page
    }

    Page buildFor(PageViewModel pageViewModel, Site site, Site owner=null) {
        Page page
        if((String)pageViewModel.id) {
            page = getById((String)pageViewModel.id)
            if(!page) {
                throw new Exception("Page not found for id ${pageViewModel.id}")
            }
        } else {
            page = new Page(site: site, owner: owner ?: site)
        }
        if(page.site?.id != site.id) {
            throw new IllegalArgumentException("PageViewModel has id of a page from another site")
        }
        buildFor(pageViewModel, page)
    }

    Iterable<Page> list(int limit=0) {
        listQuery(limit).fetch()
    }

    FeedQuery<Page> feed(Site site, boolean withDrafts=false) {
        Query<Page> q = createQuery().filter("site", site).order("-dateCreated")
        if(!withDrafts) q.filter("draft", false)
        new FeedQuery<Page>(q)
    }

    private Query<Page> listQuery(int limit, boolean drafts=false) {
        Query<Page> q = createQuery()
        if(limit) q.limit(limit)
        if(drafts) q.filter("draft", false)
        q.order("-dateCreated")
    }

    WriteResult delete(Page page) {
        for(Unit u in page.inners) {
            unitRepo.delete(u)
        }
        super.delete(page)
    }

    Key<Page> save(Page page) {
        page.name = page.name.toLowerCase()
        // Units has references on page, so we need to save one before
        if(!page.id) {
            final List<Unit> inners = page.inners
            page.inners = []
            super.save(page)
            page.inners = inners
        }
        for(Unit u in page.inners) {
            unitRepo.save(u)
            System.out.println "Saving ${u} of page"
        }
        super.save(page)
    }
}