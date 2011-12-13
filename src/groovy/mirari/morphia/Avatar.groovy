@Typed package mirari.morphia

import ru.mirari.infra.mongo.Domain
import ru.mirari.infra.mongo.BaseDao
import ru.mirari.infra.mongo.MorphiaDriver
import org.springframework.beans.factory.annotation.Autowired
import ru.mirari.infra.image.ImageHolder
import ru.mirari.infra.image.ImageFormat
import com.google.code.morphia.annotations.Indexed

/**
 * @author alari
 * @since 12/13/11 5:07 PM
 */
class Avatar extends Domain implements ImageHolder{
    transient static public final ImageFormat LARGE = new ImageFormat("210*336", "ava-large")
    transient static public final ImageFormat FEED = new ImageFormat("100*160", "ava-feed")
    transient static public final ImageFormat TINY = new ImageFormat("90*90", "ava-tiny")

    String getImagesBucket() {
        null
    }

    String getImagesPath() {
        "avatar/".concat(this.id.toString())
    }

    List<ImageFormat> getImageFormats() {
        [
                FEED,
                LARGE,
                TINY
        ]
    }

    ImageFormat getDefaultImageFormat() {
        FEED
    }
    
    boolean basic = false
    @Indexed
    String name
    
    static public class Dao extends BaseDao<Avatar> {
        @Autowired Dao(MorphiaDriver morphiaDriver) {
            super(morphiaDriver)
        }
    }
}
