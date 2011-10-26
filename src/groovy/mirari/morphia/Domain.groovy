package mirari.morphia

import com.google.code.morphia.annotations.Id
import org.bson.types.ObjectId

/**
 * @author alari
 * @since 10/26/11 10:36 PM
 */

abstract class Domain {
    @Id ObjectId id
}
