@Typed package mirari.morphia.subject

import com.google.code.morphia.annotations.Entity
import com.google.code.morphia.annotations.Id
import com.google.code.morphia.annotations.Indexed
import com.google.code.morphia.annotations.PrePersist
import com.google.code.morphia.dao.BasicDAO
import mirari.morphia.MorphiaDriver
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired

/**
 * @author Dmitry Kurinskiy
 * @since 10/1/11 1:43 PM
 */
@Entity("subjects")
abstract class Subject {
    @Id ObjectId id

    @Indexed(unique = true)
    String domain

    Date dateCreated = new Date()
    Date lastUpdated

    @PrePersist
    void prePersist() {
        lastUpdated = new Date();
    }

    boolean isPerson() {
        this.class == mirari.morphia.subject.Person
    }

    static public class Dao extends BasicDAO<mirari.morphia.subject.Subject, ObjectId> {

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

        Subject getByDomain(String domain) {
            createQuery().filter("domain", domain).get()
        }

        boolean domainExists(String domain) {
            createQuery().filter("domain", domain).countAll() > 0
        }
    }

}
