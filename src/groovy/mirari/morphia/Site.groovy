@Typed package mirari.morphia

import com.google.code.morphia.annotations.Entity
import com.google.code.morphia.annotations.Indexed
import com.google.code.morphia.annotations.PrePersist
import org.springframework.beans.factory.annotation.Autowired

import ru.mirari.infra.mongo.BaseDao
import ru.mirari.infra.mongo.Domain
import ru.mirari.infra.mongo.MorphiaDriver
import mirari.morphia.face.NamedThing
import mirari.morphia.face.AvatarHolder
import com.google.code.morphia.annotations.Reference

/**
 * @author alari
 * @since 10/27/11 8:06 PM
 */
@Entity("site")
abstract class Site extends Domain implements NamedThing, AvatarHolder {

    @Reference Avatar avatar

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

    void setName(String name) {
        this.name = name.toLowerCase()
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
