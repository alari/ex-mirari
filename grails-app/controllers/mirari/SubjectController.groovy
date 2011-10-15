package mirari

import org.springframework.beans.factory.annotation.Autowired
import mirari.morphia.subject.SubjectInfoDAO
import mirari.morphia.subject.Subject

class SubjectController extends SubjectUtilController {

  static defaultAction = "index"

  def nodeService
  def participationService
  @Autowired SubjectInfoDAO subjectInfoDao

  def index = {
    Subject subject = currentSubject
    [
        subject: currentSubject,
        info: subjectInfoDao.getBySubject(subject)]
  }
}

