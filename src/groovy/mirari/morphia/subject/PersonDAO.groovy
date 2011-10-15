package mirari.morphia.subject

import com.google.code.morphia.Key
import com.google.code.morphia.dao.BasicDAO
import grails.plugins.springsecurity.SpringSecurityService
import mirari.morphia.MorphiaDriver
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired

/**
 * @author Dmitry Kurinskiy
 * @since 10/1/11 2:12 PM
 */
class PersonDAO extends BasicDAO<mirari.morphia.subject.Person, ObjectId> {
  private transient SpringSecurityService springSecurityService
  private transient SubjectDAO subjectDao

  @Autowired PersonDAO(MorphiaDriver morphiaDriver, SpringSecurityService springSecurityService, SubjectDAO subjectDao) {
    super(morphiaDriver.mongo, morphiaDriver.morphia, morphiaDriver.dbName)
    this.springSecurityService = springSecurityService
    this.subjectDao = subjectDao
  }

  mirari.morphia.subject.Person getById(String id) {
    if (!ObjectId.isValid(id)) return null
    getById(new ObjectId(id))
  }

  Person getById(ObjectId id) {
    get(id)
  }

  Person getByDomain(String domain) {
    mirari.morphia.subject.Subject p = subjectDao.getByDomain(domain)
    p instanceof mirari.morphia.subject.Person ? (mirari.morphia.subject.Person) p : null
  }

  Key<Person> save(mirari.morphia.subject.Person person) {
    if (person.passwordChanged) {
      person.passwordHash = springSecurityService.encodePassword(person.password)
    }
    new Key<Person>(Person, subjectDao.save(person).id)
  }
}
