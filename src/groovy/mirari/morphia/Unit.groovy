@Typed package mirari.morphia

import com.google.code.morphia.dao.BasicDAO
import com.google.code.morphia.query.Query
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import com.google.code.morphia.annotations.*
import mirari.morphia.unit.AnchorUnit

/**
 * @author alari
 * @since 10/27/11 8:19 PM
 */
@Entity("unit")
@Indexes([
@Index(value = "space,name", unique = true, dropDups = true)])
abstract class Unit extends Domain implements NamedThing {
    @Reference Space space
    String name

    String title

    boolean draft = true
    @Reference Unit container

    @Reference(lazy=true) List<Unit> units

    void addUnit(Unit unit) {
        if(unit.container == null || unit.container == this) {
            unit.container = this
            if(units == null) units = []
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

    static public class Dao extends BasicDAO<Unit, ObjectId> {
        @Autowired Dao(MorphiaDriver morphiaDriver) {
            super(morphiaDriver.mongo, morphiaDriver.morphia, morphiaDriver.dbName)
        }

        Unit getById(String id) {
            if (!ObjectId.isValid(id)) return null
            getById(new ObjectId(id))
        }

        Unit getById(ObjectId id) {
            get(id)
        }

        Unit getByName(Space space, String name) {
            createQuery().filter("space", space).filter("name", name).get()
        }

        boolean nameExists(Space space, String name) {
            createQuery().filter("space", space).filter("name", name).countAll() > 0
        }

        List<Unit> getBySpace(Space space, boolean includeDrafts = false) {
            Query<Unit> q = createQuery().filter("space", space)
            if (!includeDrafts) q.filter("draft", false)
            q.fetch().collect {it}
        }

        List<Unit> getAllPublished() {
            createQuery().filter("draft", false).fetch().collect {it}
        }

        Iterable<Unit> getPublished(int limit) {
            createQuery().filter("draft", false).limit(limit).order("-lastUpdated").fetch()
        }
    }
}
