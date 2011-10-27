package mirari.morphia.unit.single

import mirari.morphia.unit.SingleUnit
import mirari.morphia.FileHolder

/**
 * @author alari
 * @since 10/27/11 8:28 PM
 */
abstract class FileUnit extends SingleUnit implements FileHolder{

    String getPath() {
        space.path + "/" + name
    }
}
