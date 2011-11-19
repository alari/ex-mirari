@Typed package mirari.morphia.unit.single

import mirari.morphia.unit.SingleUnit
import ru.mirari.infra.file.FileHolder

/**
 * @author alari
 * @since 10/27/11 8:28 PM
 */
abstract class FileUnit extends SingleUnit implements FileHolder {

    String getFilesPath() {
        this.id.toString()
    }

    String getFilesBucket() {
        null
    }

    List<String> getFileNames() {
        []
    }
}
