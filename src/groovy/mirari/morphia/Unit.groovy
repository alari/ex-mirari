@Typed package mirari.morphia

import com.google.code.morphia.query.Query
import com.mongodb.WriteResult
import org.apache.commons.lang.RandomStringUtils
import org.springframework.beans.factory.annotation.Autowired
import ru.mirari.infra.mongo.BaseDao
import ru.mirari.infra.mongo.Domain
import ru.mirari.infra.mongo.MorphiaDriver
import com.google.code.morphia.annotations.*
import mirari.ko.UnitViewModel

/**
 * @author alari
 * @since 10/27/11 8:19 PM
 */
@Entity("unit")
@Indexes([
@Index("draft"), @Index("space"),
@Index(value = "space,name", unique = true, dropDups = true)])
abstract class Unit extends Domain implements NamedThing {
    @Reference Space space
    String name = RandomStringUtils.randomAlphanumeric(5)

    String title

    boolean draft = true
    @Indexed
    @Reference Unit container

    @Reference(lazy = true) List<Unit> units

    void setViewModel(UnitViewModel viewModel) {
        title = viewModel.title
    }

    void addUnit(Unit unit) {
        if (unit.container == null || unit.container == this) {
            unit.container = this
            if (units == null) units = []
            units.add unit
        } else {
            throw new IllegalArgumentException("You should build and use anchorUnit")
        }
    }

    transient final public String type = this.getClass().simpleName.substring(0, this.getClass().simpleName.size() - 4)

    // @Version
    Long version;

    Date dateCreated = new Date();
    Date lastUpdated = new Date();

    @PrePersist
    void prePersist() {
        lastUpdated = new Date();
    }

    String toString() {
        title ?: type
    }

    static public class Dao extends BaseDao<Unit> {
        @Autowired
        Dao(MorphiaDriver morphiaDriver) {
            super(morphiaDriver)
        }

        Unit getByName(Space space, String name) {
            createQuery().filter("space", space).filter("name", name).get()
        }

        boolean nameExists(Space space, String name) {
            createQuery().filter("space", space).filter("name", name).countAll() > 0
        }

        List<Unit> getBySpace(Space space, boolean includeDrafts = false) {
            Query<Unit> q = createQuery().filter("space", space).filter("container", null).order("-lastUpdated")
            if (!includeDrafts) q.filter("draft", false)
            q.fetch().collect {it}
        }

        Query<Unit> getPubQuery() {
            createQuery().filter("container", null).filter("draft", false)
        }

        Iterable<Unit> getAllPublished() {
            pubQuery.fetch()
        }

        Iterable<Unit> getPublished(int limit) {
            pubQuery.limit(limit).order("-lastUpdated").fetch()
        }
    }
}
