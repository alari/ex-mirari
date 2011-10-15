package mirari

import mirari.morphia.subject.Subject
import mirari.morphia.subject.SubjectDAO
import org.springframework.beans.factory.annotation.Autowired

abstract class SubjectUtilController extends UtilController {
  @Autowired SubjectDAO subjectDao

  protected String getCurrentSubjectDomain() {
    params.domain
  }

  protected Subject getCurrentSubject() {
    subjectDao.getByDomain(currentSubjectDomain)
  }
}
