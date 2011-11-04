@Typed package mirari.morphia

import com.google.code.morphia.annotations.Entity
import com.google.code.morphia.annotations.Indexed
import com.google.code.morphia.annotations.PrePersist
import com.google.code.morphia.dao.BasicDAO
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import mirari.util.file.FileHolder

/**
 * @author alari
 * @since 10/27/11 8:06 PM
 */
@Entity("space")
abstract class Space extends Domain implements NamedThing {
    @Indexed(unique = true)
    String name

    Date dateCreated = new Date()
    Date lastUpdated

    @PrePersist
    void prePersist() {
        lastUpdated = new Date();
    }

    static public class Dao extends BasicDAO<Space, ObjectId> {

        @Autowired
        Dao(MorphiaDriver morphiaDriver) {
            super(morphiaDriver.mongo, morphiaDriver.morphia, morphiaDriver.dbName)
        }

        Space getById(String id) {
            if (!ObjectId.isValid(id)) return null
            getById(new ObjectId(id))
        }

        Space getById(ObjectId id) {
            get(id)
        }

        Space getByName(String name) {
            createQuery().filter("name", name).get()
        }

        boolean nameExists(String name) {
            createQuery().filter("name", name).countAll() > 0
        }
    }

}
