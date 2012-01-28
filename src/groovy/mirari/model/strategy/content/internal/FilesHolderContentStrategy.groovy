@Typed package mirari.model.strategy.content.internal

import mirari.model.strategy.content.ContentHolder
import org.springframework.beans.factory.annotation.Autowired
import ru.mirari.infra.FileStorageService
import ru.mirari.infra.file.FileHolder

/**
 * @author alari
 * @since 1/6/12 5:53 PM
 */
abstract class FilesHolderContentStrategy extends InternalContentStrategy {
    @Autowired
    protected FileStorageService fileStorageService

    protected Holder getFileHolder(ContentHolder unit) {
        new Holder(unit.stringId)
    }

    static public class Holder implements FileHolder {
        final public String unitId
        final public String filesPath

        List<String> fileNames = []
        String filesBucket = null

        Holder(String unitId) {
            this.unitId = unitId
            this.filesPath = "f/".concat(unitId)
        }

        String getFilesPath() {
            filesPath
        }
    }
}
