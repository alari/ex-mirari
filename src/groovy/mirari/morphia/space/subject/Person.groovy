@Typed package mirari.morphia.space.subject

import com.google.code.morphia.Key
import com.google.code.morphia.annotations.Embedded
import com.google.code.morphia.annotations.Indexed
import grails.plugins.springsecurity.SpringSecurityService
import mirari.morphia.space.Subject
import org.springframework.beans.factory.annotation.Autowired
import ru.mirari.infra.mongo.BaseDao
import ru.mirari.infra.mongo.MorphiaDriver

/**
 * @author Dmitry Kurinskiy
 * @since 10/1/11 2:09 PM
 */
class Person extends Subject {

    private String password
    @Indexed(unique = true)
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
        "@" + (displayName ?: name)
    }

    static public class Dao extends BaseDao<Person> {
        @Autowired private transient SpringSecurityService springSecurityService
        @Autowired private transient Subject.Dao subjectDao

        @Autowired
        Dao(MorphiaDriver morphiaDriver, SpringSecurityService springSecurityService, Subject.Dao subjectDao) {
            super(morphiaDriver)
        }

        Person getByName(String name) {
            Subject p = subjectDao.getByName(name)
            p instanceof Person ? (Person) p : null
        }

        Key<Person> save(Person person) {
            if (person.passwordChanged) {
                person.passwordHash = springSecurityService.encodePassword(person.password)
            }
            new Key<Person>(Person, subjectDao.save(person).id)
        }

        Person getByEmail(String email) {
            createQuery().filter("email", email).get()
        }

        boolean emailExists(String email) {
            createQuery().filter("email", email).countAll() > 0
        }
    }
}
