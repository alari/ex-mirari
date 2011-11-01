package mirari

import mirari.morphia.space.Subject
import mirari.morphia.space.subject.SubjectInfo
import org.springframework.beans.factory.annotation.Autowired

class SpaceController extends SpaceUtilController {

    static defaultAction = "index"

    def nodeService
    def participationService
    @Autowired SubjectInfo.Dao subjectInfoDao
    def fileStorageService

    def index = {
        Subject subject = currentSpace
        [
                avatarUrl: fileStorageService.getUrl(currentSpaceName, "avatar.png"),
                subject: currentSpace,
                info: subjectInfoDao.getBySubject(subject)]
    }
}

