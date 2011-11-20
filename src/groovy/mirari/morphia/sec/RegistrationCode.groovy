@Typed package mirari.morphia.sec

import com.google.code.morphia.annotations.Indexed
import mirari.morphia.Routine
import org.springframework.beans.factory.annotation.Autowired
import ru.mirari.infra.mongo.BaseDao
import ru.mirari.infra.mongo.MorphiaDriver

/**
 * @author Dmitry Kurinskiy
 * @since 10/1/11 1:24 PM
 */
class RegistrationCode extends Routine {
    String name

    @Indexed
    String token = UUID.randomUUID().toString().replaceAll('-', '')

    Date dateCreated = new Date()

    static public class Dao extends BaseDao<RegistrationCode> {

        @Autowired
        Dao(MorphiaDriver morphiaDriver) {
            super(morphiaDriver)
        }

        RegistrationCode getByToken(String token) {
            createQuery().filter("token", token).get()
        }
    }
}
