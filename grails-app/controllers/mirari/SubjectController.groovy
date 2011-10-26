package mirari

import mirari.morphia.subject.Subject
import mirari.morphia.subject.SubjectInfo
import org.springframework.beans.factory.annotation.Autowired

class SubjectController extends SubjectUtilController {

  static defaultAction = "index"

  def nodeService
  def participationService
  @Autowired SubjectInfo.Dao subjectInfoDao
  def fileStorageService

  def index = {
    Subject subject = currentSubject
    [
        avatarUrl: fileStorageService.getUrl(currentSubjectDomain, "avatar.png"),
        subject: currentSubject,
        info: subjectInfoDao.getBySubject(subject)]
  }
}

