package mirari

import mirari.morphia.space.Subject
import org.springframework.beans.factory.annotation.Autowired

abstract class SpaceUtilController extends UtilController {
  @Autowired Subject.Dao subjectDao

  protected String getCurrentSpaceName() {
    params.spaceName
  }

  protected Subject getCurrentSubject() {
    subjectDao.getByName(currentSpaceName)
  }
}
