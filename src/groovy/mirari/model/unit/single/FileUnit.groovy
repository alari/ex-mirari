@Typed package mirari.model.unit.single

import mirari.model.unit.SingleUnit
import ru.mirari.infra.file.FileHolder

/**
 * @author alari
 * @since 10/27/11 8:28 PM
 */
abstract class FileUnit extends SingleUnit implements FileHolder {

    String getFilesPath() {
        "u/".concat this.id.toString()
    }

    String getFilesBucket() {
        null
    }

    List<String> getFileNames() {
        []
    }
}
