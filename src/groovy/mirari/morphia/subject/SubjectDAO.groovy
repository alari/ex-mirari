@Typed package mirari.morphia.subject

import com.google.code.morphia.dao.BasicDAO
import mirari.morphia.MorphiaDriver
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired

/**
 * @author Dmitry Kurinskiy
 * @since 10/1/11 1:48 PM
 */
class SubjectDAO extends BasicDAO<mirari.morphia.subject.Subject, ObjectId> {

  @Autowired SubjectDAO(MorphiaDriver morphiaDriver) {
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
