package mirari.morphia

import ru.mirari.infra.mongo.Domain
import com.google.code.morphia.annotations.*
import mirari.morphia.space.subject.Person
import org.apache.commons.lang.RandomStringUtils
import ru.mirari.infra.mongo.BaseDao
import ru.mirari.infra.mongo.MorphiaDriver
import org.springframework.beans.factory.annotation.Autowired

/**
 * @author alari
 * @since 11/28/11 1:29 PM
 */
@Entity("page")
class Page extends Domain {
    // where (site)
    @Indexed
    @Reference Space space
    // who
    @Reference Person person
    // what
    @Reference(lazy = true) List<Unit> inners = []
    // named after
    String name = RandomStringUtils.randomAlphanumeric(5)
    String title
    // permissions
    boolean draft = true
    // kind of
    String type = "page"
    // when
    Date dateCreated = new Date();
    Date lastUpdated = new Date();

    @PrePersist
    void prePersist() {
        lastUpdated = new Date();
    }

    static public class Dao extends BaseDao<Page> {
        @Autowired
        Dao(MorphiaDriver morphiaDriver) {
            super(morphiaDriver)
        }
    }
}
