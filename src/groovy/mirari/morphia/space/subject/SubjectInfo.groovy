@Typed package mirari.morphia.space.subject

import com.google.code.morphia.annotations.Entity
import com.google.code.morphia.annotations.Id
import com.google.code.morphia.annotations.Reference
import com.google.code.morphia.dao.BasicDAO
import mirari.morphia.MorphiaDriver
import mirari.morphia.space.Subject
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired

/**
 * @author Dmitry Kurinskiy
 * @since 10/1/11 1:46 PM
 */
@Entity
class SubjectInfo {
    @Id ObjectId id

    String frontText = ""

    @Reference(lazy = true)
    Subject subject

    static public class Dao extends BasicDAO<SubjectInfo, ObjectId> {
        @Autowired Dao(MorphiaDriver morphiaDriver) {
            super(morphiaDriver.mongo, morphiaDriver.morphia, morphiaDriver.dbName)
        }

        mirari.morphia.space.subject.SubjectInfo getById(String id) {
            if (!ObjectId.isValid(id)) return null
            getById(new ObjectId(id))
        }

        mirari.morphia.space.subject.SubjectInfo getById(ObjectId id) {
            get(id)
        }

        mirari.morphia.space.subject.SubjectInfo getBySubject(Subject subject) {
            mirari.morphia.space.subject.SubjectInfo info = createQuery().filter("subject", subject).get()
            if (!info) {
                info = new SubjectInfo(subject: subject)
                save(info)
            }
            info
        }
    }
}
