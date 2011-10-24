package mirari

import grails.util.Environment
import mirari.util.file.FileStorage
import org.springframework.beans.factory.annotation.Autowired

class FileStorageService {

  static transactional = false

  @Autowired FileStorage s3FileStorage
  @Autowired FileStorage localFileStorage

  private FileStorage getFileStorage() {
    Environment.isWarDeployed() ? s3FileStorage : localFileStorage
  }

  /**
   * Stores a file in a static storage;
   *
   * @param file
   * @param relativePath to put into the storage root
   * @param filename to rename the file
   */
  void store(File file, String relativePath, String filename = null) {
    fileStorage.store(file, relativePath, filename, null)
  }

  void delete(String relativePath, String filename) {
    fileStorage.delete(relativePath, filename, null)
  }

  String getUrl(String relativePath, String filename) {
    fileStorage.getUrl(relativePath, filename, null)
  }
}
