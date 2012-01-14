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
import mirari.model.Tag

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

    Iterable<Page> list(int limit=0) {
        Query<Page> q = createQuery()
        if(limit) q.limit(limit)
        q.filter("draft", false).order("-dateCreated").fetch()
    }

    FeedQuery<Page> feed(Site site, boolean withDrafts=false) {
        Query<Page> q = createQuery().filter("site", site).order("-dateCreated")
        if(!withDrafts) q.filter("draft", false)
        new FeedQuery<Page>(q)
    }

    @Override
    FeedQuery<Page> feed(Tag tag, boolean withDrafts=false) {
        Query<Page> q = createQuery().filter("tags", tag).order("-dateCreated")
        if(!withDrafts) q.filter("draft", false)
        new FeedQuery<Page>(q)
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
        if(!page.isPersisted()) {
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