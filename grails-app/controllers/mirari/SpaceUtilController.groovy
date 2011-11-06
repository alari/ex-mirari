package mirari

import mirari.morphia.Space
import org.springframework.beans.factory.annotation.Autowired

abstract class SpaceUtilController extends UtilController {
    @Autowired Space.Dao spaceDao

    protected String getCurrentSpaceName() {
        params.spaceName
    }

    protected Space getCurrentSpace() {
        params.space
    }
}
