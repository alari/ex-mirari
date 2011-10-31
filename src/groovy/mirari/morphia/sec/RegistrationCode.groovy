@Typed package mirari.morphia.sec

import com.google.code.morphia.annotations.Indexed
import com.google.code.morphia.dao.BasicDAO
import mirari.morphia.MorphiaDriver
import mirari.morphia.Routine
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired

/**
 * @author Dmitry Kurinskiy
 * @since 10/1/11 1:24 PM
 */
class RegistrationCode extends Routine {
    String name

    @Indexed
    String token = UUID.randomUUID().toString().replaceAll('-', '')

    Date dateCreated = new Date()

    static public class Dao extends BasicDAO<RegistrationCode, ObjectId> {
        @Autowired
        Dao(MorphiaDriver morphiaDriver) {
            super(morphiaDriver.mongo, morphiaDriver.morphia, morphiaDriver.dbName)
        }

        RegistrationCode getByToken(String token) {
            createQuery().filter("token", token).get()
        }
    }
}
