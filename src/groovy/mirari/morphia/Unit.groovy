@Typed package mirari.morphia

import com.google.code.morphia.annotations.Entity
import com.google.code.morphia.annotations.Index
import com.google.code.morphia.annotations.Indexes
import javax.persistence.PrePersist
import javax.persistence.Version
import com.google.code.morphia.dao.BasicDAO
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired

/**
 * @author alari
 * @since 10/27/11 8:19 PM
 */
@Entity("unit")
@Indexes([
@Index(value = "space,name", unique = true, dropDups = true)])
abstract class Unit extends Domain implements NamedThing{
    Space space
    String name

    String title

    boolean draft = true
    Unit container

    final public String type = this.getClass().simpleName.substring(0, -4)

    @Version
    Long version;

    Date dateCreated = new Date();
    Date lastUpdated = new Date();

    @PrePersist
    void prePersist() {
        lastUpdated = new Date();
    }

    static public class Dao extends BasicDAO<Unit, ObjectId>{
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
    }
}
