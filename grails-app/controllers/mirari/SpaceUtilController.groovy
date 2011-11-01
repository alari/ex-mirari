package mirari

import mirari.morphia.space.Subject
import org.springframework.beans.factory.annotation.Autowired
import mirari.morphia.Space

abstract class SpaceUtilController extends UtilController {
    @Autowired Space.Dao spaceDao

    protected String getCurrentSpaceName() {
        params.spaceName
    }

    protected Subject getCurrentSpace() {
        spaceDao.getByName(currentSpaceName)
    }
}
