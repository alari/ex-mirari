package mirari

import mirari.morphia.space.Subject
import mirari.morphia.space.subject.SubjectInfo
import org.springframework.beans.factory.annotation.Autowired
import mirari.morphia.Unit

class SpaceController extends SpaceUtilController {

    static defaultAction = "index"
    def subjectInfoDao

    Unit.Dao unitDao

    def index = {
        [
                info: subjectInfoDao.getBySubject((Subject)currentSpace),
                allUnits: unitDao.getBySpace(currentSpace)
        ]
    }
}

