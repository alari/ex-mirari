@Typed package mirari.morphia.subject

import com.google.code.morphia.Key
import com.google.code.morphia.annotations.Embedded
import com.google.code.morphia.dao.BasicDAO
import grails.plugins.springsecurity.SpringSecurityService
import mirari.morphia.MorphiaDriver
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired

/**
 * @author Dmitry Kurinskiy
 * @since 10/1/11 2:09 PM
 */
class Person extends Subject {

    private String password
    String email
    boolean enabled
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired

    transient public boolean passwordChanged

    String getPassword() {
        password
    }

    void setPassword(String password) {
        this.password = password
        passwordChanged = true
    }

    void setPasswordHash(String hash) {
        password = hash
        passwordChanged = false
    }

    @Embedded Set<Role> authorities = []

    String toString() {
        "@" + domain
    }

    static public class Dao extends BasicDAO<Person, ObjectId> {
        private transient SpringSecurityService springSecurityService
        private transient Subject.Dao subjectDao

        @Autowired Dao(MorphiaDriver morphiaDriver, SpringSecurityService springSecurityService, Subject.Dao subjectDao) {
            super(morphiaDriver.mongo, morphiaDriver.morphia, morphiaDriver.dbName)
            this.springSecurityService = springSecurityService
            this.subjectDao = subjectDao
        }

        Person getById(String id) {
            if (!ObjectId.isValid(id)) return null
            getById(new ObjectId(id))
        }

        Person getById(ObjectId id) {
            get(id)
        }

        Person getByDomain(String domain) {
            mirari.morphia.subject.Subject p = subjectDao.getByDomain(domain)
            p instanceof Person ? (Person) p : null
        }

        Key<Person> save(Person person) {
            if (person.passwordChanged) {
                person.passwordHash = springSecurityService.encodePassword(person.password)
            }
            new Key<Person>(Person, subjectDao.save(person).id)
        }
    }
}
