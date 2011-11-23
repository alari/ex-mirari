@Typed package mirari.morphia.unit.single

import mirari.morphia.unit.SingleUnit
import com.google.code.morphia.annotations.*
import ru.mirari.infra.mongo.BaseDao
import ru.mirari.infra.mongo.MorphiaDriver
import org.springframework.beans.factory.annotation.Autowired
import ru.mirari.infra.mongo.Domain

/**
 * @author alari
 * @since 11/22/11 9:20 PM
 */
class TextUnit extends SingleUnit{
    @Reference(lazy=true) TextUnit.Content content

    @Entity("units.textContent")
    static public class Content extends Domain{
        String text

        static public class Dao extends BaseDao<TextUnit.Content>{
            @Autowired Dao(MorphiaDriver morphiaDriver) {
                super(morphiaDriver)
            }
        }
    }
}
