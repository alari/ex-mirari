@Typed package mirari.morphia.unit.single

import mirari.morphia.FileHolder
import mirari.morphia.unit.SingleUnit

/**
 * @author alari
 * @since 10/27/11 8:28 PM
 */
abstract class FileUnit extends SingleUnit implements FileHolder {

    String getPath() {
        this.id.toString()
    }
}
