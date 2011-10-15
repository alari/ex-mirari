package mirari

import org.springframework.beans.factory.annotation.Autowired
import mirari.morphia.subject.SubjectDAO
import mirari.morphia.subject.Subject

abstract class SubjectUtilController extends UtilController {
  @Autowired SubjectDAO subjectDao

  protected String getCurrentSubjectDomain() {
    params.domain
  }

  protected Subject getCurrentSubject() {
    subjectDao.getByDomain(currentSubjectDomain)
  }
}
