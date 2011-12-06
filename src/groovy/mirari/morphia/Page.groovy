@Typed package mirari.morphia

import ru.mirari.infra.mongo.Domain
import com.google.code.morphia.annotations.*
import mirari.morphia.space.subject.Person
import org.apache.commons.lang.RandomStringUtils
import ru.mirari.infra.mongo.BaseDao
import ru.mirari.infra.mongo.MorphiaDriver
import org.springframework.beans.factory.annotation.Autowired
import mirari.ko.PageViewModel
import mirari.ko.UnitViewModel
import com.google.code.morphia.Key
import org.apache.log4j.Logger
import com.google.code.morphia.query.Query
import com.mongodb.WriteResult

/**
 * @author alari
 * @since 11/28/11 1:29 PM
 */
@Entity("page")
@Indexes([
        @Index("space"), @Index("-lastUpdated"), @Index("draft"),
        @Index(value = "space,name", unique=true, dropDups=true)
])
class Page extends Domain implements NamedThing, RightsControllable {
    // where (site)
    @Reference Space space
    // who
    @Reference Person person
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

    static public class Dao extends BaseDao<Page> {
        @Autowired Unit.Dao unitDao
        static final private Logger log = Logger.getLogger(this)

        @Autowired
        Dao(MorphiaDriver morphiaDriver) {
            super(morphiaDriver)
        }

        Page getByName(Space space, String name) {
            createQuery().filter("space", space).filter("name", name.toLowerCase()).get()
        }

        Page buildFor(PageViewModel pageViewModel, Space space) {
            Page page
            if((String)pageViewModel.id) {
                page = getById((String)pageViewModel.id)
                if(!page) {
                    throw new Exception("Page not found for id ${pageViewModel.id}")
                }
            } else {
                page = new Page(space: space)
            }
            if(page.space?.id != space.id) {
                throw new IllegalArgumentException("PageViewModel has id of a page from another space")
            }
            pageViewModel.assignTo(page)
            // TODO: it might be an old page
            for(UnitViewModel uvm in pageViewModel.inners) {
                // TODO: remove units with _remove
                page.inners.add unitDao.buildFor(uvm, space)
                // Todo: external units must be asserted via anchors
            }
            page
        }

        Iterable<Page> list(int limit=0) {
            listQuery(limit).fetch()
        }

        Iterable<Page> list(Space space, int limit=0) {
            listQuery(limit).filter("space", space).fetch()
        }

        Iterable<Page> listWithDrafts(Space space, int limit=0) {
            listQuery(limit, true).filter("space", space).fetch()
        }

        private Query<Page> listQuery(int limit, boolean drafts=false) {
            Query<Page> q = createQuery()
            if(limit) q.limit(limit)
            if(drafts) q.filter("draft", false)
            q.order("-lastUpdated")
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
            }
            log.error page
            log.error page.inners
            super.save(page)
        }
    }
}
