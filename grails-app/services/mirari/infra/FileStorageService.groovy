package mirari.infra

import grails.util.Environment
import mirari.morphia.FileHolder
import mirari.util.file.FileStorage
import org.springframework.beans.factory.annotation.Autowired

class FileStorageService {

    static transactional = false

    @Autowired FileStorage fileStorage

    void store(File file, final FileHolder holder, String filename = null) {
        store(file, holder.path, filename)
    }

    void store(File file, String relativePath, String filename = null) {
        fileStorage.store(file, relativePath, filename, null)
    }

    void delete(final FileHolder holder, String filename) {
        delete(holder.path, filename)
    }

    void delete(String relativePath, String filename) {
        fileStorage.delete(relativePath, filename, null)
    }

    String getUrl(final FileHolder holder, String filename) {
        getUrl(holder.path, filename)
    }

    String getUrl(String relativePath, String filename) {
        fileStorage.getUrl(relativePath, filename, null)
    }
}
