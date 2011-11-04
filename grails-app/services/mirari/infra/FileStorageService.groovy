package mirari.infra

import mirari.util.file.FileHolder
import mirari.util.file.FileStorage
import org.springframework.beans.factory.annotation.Autowired

class FileStorageService {

    static transactional = false

    @Autowired FileStorage fileStorage

    void store(File file, final FileHolder holder, String filename = null) {
        store(file, holder.filesPath, filename)
    }

    void store(File file, String relativePath, String filename = null) {
        fileStorage.store(file, relativePath, filename, null)
    }

    void delete(final FileHolder holder, String filename) {
        delete(holder.filesPath, filename)
    }

    void delete(String relativePath, String filename) {
        fileStorage.delete(relativePath, filename, null)
    }

    String getUrl(final FileHolder holder, String filename) {
        getUrl(holder.filesPath, filename)
    }

    String getUrl(String relativePath, String filename) {
        fileStorage.getUrl(relativePath, filename, null)
    }
}
