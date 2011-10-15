@Typed package mirari.morphia.subject

import mirari.morphia.MorphiaDriver

import com.google.code.morphia.dao.BasicDAO
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired

/**
 * @author Dmitry Kurinskiy
 * @since 10/1/11 2:06 PM
 */
class SubjectInfoDAO  extends BasicDAO<SubjectInfo, ObjectId> {
  @Autowired SubjectInfoDAO(MorphiaDriver morphiaDriver) {
    super(morphiaDriver.mongo, morphiaDriver.morphia, morphiaDriver.dbName)
  }

  mirari.morphia.subject.SubjectInfo getById(String id) {
    if (!ObjectId.isValid(id)) return null
    getById(new ObjectId(id))
  }

  mirari.morphia.subject.SubjectInfo getById(ObjectId id) {
    get(id)
  }

  mirari.morphia.subject.SubjectInfo getBySubject(mirari.morphia.subject.Subject subject) {
    mirari.morphia.subject.SubjectInfo info = createQuery().filter("subject", subject).get()
    if(!info) {
      info = new SubjectInfo(subject: subject)
      save(info)
    }
    info
  }
}