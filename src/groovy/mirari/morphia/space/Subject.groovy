@Typed package mirari.morphia.space

import com.google.code.morphia.annotations.Entity
import com.google.code.morphia.dao.BasicDAO
import mirari.morphia.MorphiaDriver
import mirari.morphia.Space
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import mirari.morphia.space.subject.Person

/**
 * @author Dmitry Kurinskiy
 * @since 10/1/11 1:43 PM
 */
@Entity("subjects")
abstract class Subject extends Space {

    boolean isPerson() {
        this.class == Person
    }

    static public class Dao extends BasicDAO<Subject, ObjectId> {

        @Autowired Dao(MorphiaDriver morphiaDriver) {
            super(morphiaDriver.mongo, morphiaDriver.morphia, morphiaDriver.dbName)
        }

        Subject getById(String id) {
            if (!ObjectId.isValid(id)) return null
            getById(new ObjectId(id))
        }

        Subject getById(ObjectId id) {
            get(id)
        }

        Subject getByName(String name) {
            createQuery().filter("name", name).get()
        }

        boolean nameExists(String name) {
            createQuery().filter("name", name).countAll() > 0
        }
    }

}
