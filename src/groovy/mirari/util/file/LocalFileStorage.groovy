@Typed package mirari.util.file

import mirari.util.ConfigReader
import org.apache.commons.io.FileUtils
import org.springframework.beans.factory.annotation.Autowired

/**
 * @author Dmitry Kurinskiy
 * @since 21.10.11 12:40
 */
class LocalFileStorage implements FileStorage {

  String defaultBucket = "storage"
  String localRoot = "./web-app/"
  String urlRoot

  @Autowired
  LocalFileStorage(ConfigReader configReader) {
    localRoot = configReader.read("grails.mirari.fileStorage.local.localRoot", localRoot)
    defaultBucket = configReader.read("grails.mirari.fileStorage.local.defaultBucket", localRoot)
    urlRoot = configReader.read("grails.mirari.fileStorage.local.urlRoot")
    if (!urlRoot) {
      urlRoot = configReader.read("grails.serverURL").toString()
    }
    if (!urlRoot.endsWith("/")) urlRoot = urlRoot.concat("/")
  }

  void store(File file, String path, String filename, String bucket) {
    createDir(path, bucket)
    File newFile = new File(getFullLocalPath(path, filename ?: file.name, bucket ?: defaultBucket))

    FileUtils.copyFile(file, newFile)
  }

  void delete(String path, String filename, String bucket) {
    new File(getFullLocalPath(path, filename, bucket)).delete()
  }

  String getUrl(String path, String filename, String bucket) {
    urlRoot + getFullPath(path, filename, bucket)
  }

  private String getFullLocalPath(String path, String filename, String bucket) {
    return localRoot + getFullPath(path, filename, bucket)
  }

  private String getFullPath(String path, String filename, String bucket) {
    String fullPath = bucket ?: defaultBucket

    if (!fullPath.endsWith("/")) fullPath = fullPath.concat("/")
    fullPath = fullPath.concat(path)

    if (!fullPath.endsWith("/")) fullPath = fullPath.concat("/")
    fullPath = fullPath.concat(filename)

    fullPath
  }

  private void createDir(String path, String bucket) {
    new File(localRoot + (bucket ?: defaultBucket) + "/" + path).mkdirs()
  }
}
