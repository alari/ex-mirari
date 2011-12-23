@Typed package mirari.morphia

import ru.mirari.infra.mongo.Domain
import com.google.code.morphia.annotations.*

import org.apache.commons.lang.RandomStringUtils
import ru.mirari.infra.mongo.BaseDao
import ru.mirari.infra.mongo.MorphiaDriver
import org.springframework.beans.factory.annotation.Autowired
import mirari.ko.PageViewModel

import com.google.code.morphia.Key
import org.apache.log4j.Logger
import com.google.code.morphia.query.Query
import com.mongodb.WriteResult
import mirari.morphia.face.UnitsContainer
import mirari.morphia.face.RightsControllable
import mirari.morphia.face.NamedThing
import mirari.Pagination

/**
 * @author alari
 * @since 11/28/11 1:29 PM
 */
@Entity("page")
@Indexes([
        @Index("site"), @Index("-lastUpdated"), @Index("draft"),
        @Index(value = "site,name", unique=true, dropDups=true)
])
class Page extends Domain implements NamedThing, RightsControllable, UnitsContainer {
    // where (site)
    @Reference Site site
    // who
    @Reference Site owner
    // what
    @Reference(lazy = true) List<Unit> inners = []
    // named after
    String name = RandomStringUtils.randomAlphanumeric(5).toLowerCase()
    String title
    // permissions
    boolean draft = true
    // kind of
    String type = "page"
    // when
    Date dateCreated = new Date();
    Date lastUpdated = new Date();

    @PrePersist
    void prePersist() {
        lastUpdated = new Date();
    }

    String toString() {
        title ?: type
    }
    
    void attach(Unit unit) {
        inners.add(unit)
    }

    PageViewModel getViewModel(){
        PageViewModel uvm = new PageViewModel(
                id: id.toString(),
                title: title,
                type: type,
                draft: draft,
                inners: []
        )
        for(Unit u : inners) {
            uvm.inners.add u.viewModel
        }
        uvm
    }
    
    void setViewModel(PageViewModel vm) {
        vm.assignTo(this)
    }

    static public class Dao extends BaseDao<Page> {
        @Autowired Unit.Dao unitDao
        static final private Logger log = Logger.getLogger(this)

        @Autowired
        Dao(MorphiaDriver morphiaDriver) {
            super(morphiaDriver)
        }

        Page getByName(Site site, String name) {
            createQuery().filter("site", site).filter("name", name.toLowerCase()).get()
        }
        
        Page buildFor(PageViewModel pageViewModel, Page page) {
            pageViewModel.assignTo(page)

            Map<String, Unit> oldUnits = unitDao.collectUnits(page)
            unitDao.attachUnits(page, pageViewModel.inners, page, oldUnits)
            // TODO: the rest of units must be deleted or modified if they have anchors
            println "The rest of units: ${oldUnits}"
            for(Unit u in oldUnits.values()) {
                unitDao.delete(u)
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

        @Deprecated
        Iterable<Page> list(Site site, int limit=0) {
            listQuery(limit).filter("site", site).fetch()
        }
        
        FeedQuery feed(Site site, boolean withDrafts=false) {
            Query<Page> q = createQuery().filter("site", site).order("-dateCreated")
            if(!withDrafts) q.filter("draft", false)
            new FeedQuery(q)
        }

        @Deprecated
        Iterable<Page> listWithDrafts(Site site, int limit=0) {
            listQuery(limit, true).filter("site", site).fetch()
        }

        private Query<Page> listQuery(int limit, boolean drafts=false) {
            Query<Page> q = createQuery()
            if(limit) q.limit(limit)
            if(drafts) q.filter("draft", false)
            q.order("-dateCreated")
        }

        WriteResult delete(Page page) {
            for(Unit u in page.inners) {
                unitDao.delete(u)
            }
            super.delete(page)
        }

        Key<Page> save(Page page) {
            page.name = page.name.toLowerCase()
            for(Unit u in page.inners) {
                unitDao.save(u)
                System.out.println "Saving ${u} of page"
            }
            super.save(page)
        }
    }
    static public class FeedQuery implements Iterable<Page>{
        private Query<Page> query
        private long total
        private int page
        private int perPage
        private int pageCount

        private Pagination pagination
        
        FeedQuery(Query<Page> query) {
            this.query = query
            total = query.countAll()
        }
        
        FeedQuery paginate(int page, int perPage=5) {
            query = query.offset(page*perPage).limit(perPage)
            total = query.countAll()
            pagination = new Pagination((int)Math.ceil(total/perPage), page)
            this
        }

        Pagination getPagination() {
            pagination
        }

        long getTotal() {
            if(total == null) {
                total = query.countAll()
            }
            total
        }

        @Override
        Iterator<Page> iterator() {
            query.fetch().iterator()
        }
    }
}
