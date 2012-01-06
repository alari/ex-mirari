package mirari.model.strategy.content.impl

import mirari.model.Unit
import ru.mirari.infra.file.FileHolder
import ru.mirari.infra.FileStorageService
import mirari.util.ApplicationContextHolder
import mirari.model.strategy.content.ContentStrategy

/**
 * @author alari
 * @since 1/6/12 5:53 PM
 */
abstract class FilesHolderContentStrategy extends ContentStrategy {
    static final protected FileStorageService fileStorageService

    static {
        fileStorageService = (FileStorageService) ApplicationContextHolder.getBean("fileStorageService")
    }

    protected Holder getFileHolder(Unit unit) {
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
