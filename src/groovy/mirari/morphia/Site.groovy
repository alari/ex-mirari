@Typed package mirari.morphia

import com.google.code.morphia.annotations.Entity
import com.google.code.morphia.annotations.Indexed
import com.google.code.morphia.annotations.PrePersist
import org.springframework.beans.factory.annotation.Autowired
import ru.mirari.infra.image.ImageFormat
import ru.mirari.infra.image.ImageHolder
import ru.mirari.infra.mongo.BaseDao
import ru.mirari.infra.mongo.Domain
import ru.mirari.infra.mongo.MorphiaDriver

/**
 * @author alari
 * @since 10/27/11 8:06 PM
 */
@Entity("site")
abstract class Site extends Domain implements ImageHolder, NamedThing {
    transient static public final ImageFormat AVA_LARGE = new ImageFormat("210*336", "ava-large")
    transient static public final ImageFormat AVA_FEED = new ImageFormat("100*160", "ava-feed")
    transient static public final ImageFormat AVA_TINY = new ImageFormat("90*90", "ava-tiny")

    String getImagesBucket() {
        null
    }

    String getImagesPath() {
        this.id.toString()
    }

    List<ImageFormat> getImageFormats() {
        [
                AVA_FEED,
                AVA_LARGE,
                AVA_TINY
        ]
    }

    ImageFormat getDefaultImageFormat() {
        AVA_FEED
    }

    @Indexed(unique = true)
    String name

    @Indexed(unique = true)
    String displayName

    Date dateCreated = new Date()
    Date lastUpdated

    @PrePersist
    void prePersist() {
        lastUpdated = new Date();
    }

    String toString() {
        "@" + (displayName ?: name)
    }

    static public class Dao extends BaseDao<Site> {
        @Autowired Dao(MorphiaDriver morphiaDriver){
            super(morphiaDriver)
        }

        Site getByName(String name) {
            createQuery().filter("name", name.toLowerCase()).get()
        }

        boolean nameExists(String name) {
            createQuery().filter("name", name.toLowerCase()).countAll() > 0
        }
    }

}
