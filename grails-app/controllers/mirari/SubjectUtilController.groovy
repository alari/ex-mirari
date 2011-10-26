package mirari

import mirari.morphia.subject.Subject
import org.springframework.beans.factory.annotation.Autowired

abstract class SubjectUtilController extends UtilController {
  @Autowired Subject.Dao subjectDao

  protected String getCurrentSubjectDomain() {
    params.domain
  }

  protected Subject getCurrentSubject() {
    subjectDao.getByDomain(currentSubjectDomain)
  }
}
