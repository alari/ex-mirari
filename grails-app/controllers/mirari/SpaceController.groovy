package mirari

import mirari.morphia.space.Subject
import mirari.morphia.space.subject.SubjectInfo
import org.springframework.beans.factory.annotation.Autowired

class SpaceController extends SpaceUtilController {

    static defaultAction = "index"
    def subjectInfoDao

    def index = {
        [info: subjectInfoDao.getBySubject((Subject)currentSpace)]
    }
}

