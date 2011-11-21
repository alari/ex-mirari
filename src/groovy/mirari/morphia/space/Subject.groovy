@Typed package mirari.morphia.space

import mirari.morphia.Space
import mirari.morphia.space.subject.Person
import org.springframework.beans.factory.annotation.Autowired
import ru.mirari.infra.mongo.BaseDao
import ru.mirari.infra.mongo.MorphiaDriver

/**
 * @author Dmitry Kurinskiy
 * @since 10/1/11 1:43 PM
 */
abstract class Subject extends Space {

    boolean isPerson() {
        this.class == Person
    }

    static public class Dao extends BaseDao<Subject> {

        @Autowired
        Dao(MorphiaDriver morphiaDriver) {
            super(morphiaDriver)
        }

        Subject getByName(String name) {
            createQuery().filter("name", name).get()
        }

        boolean nameExists(String name) {
            createQuery().filter("name", name).countAll() > 0
        }
    }

}
