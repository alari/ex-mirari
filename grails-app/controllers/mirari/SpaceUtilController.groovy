package mirari

import org.springframework.beans.factory.annotation.Autowired
import mirari.morphia.Site

abstract class SpaceUtilController extends UtilController {
    @Autowired Site.Dao spaceDao

    protected String getCurrentSpaceName() {
        params.spaceName
    }

    protected Site getCurrentSpace() {
        params.space
    }
}
