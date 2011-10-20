package mirari

import org.jets3t.service.impl.rest.httpclient.RestS3Service
import org.jets3t.service.security.AWSCredentials
import org.jets3t.service.model.S3Object
import org.jets3t.service.acl.AccessControlList

class StaticFileService {

  static transactional = false

  def aws
  def grailsApplication

  private String getDefaultBucket(){
    "s.mirari.ru"
  }

  private RestS3Service getS3Service(){
    def c = grailsApplication.config.grails.plugin.aws.credentials
    new RestS3Service(new AWSCredentials(c.accessKey, c.secretKey))
  }

  /**
   * Stores a file in a static storage;
   *
   * @param file
   * @param relativePath to put into the storage root
   * @param filename
   */
  void store(File file, String relativePath, String filename=null) {
    S3Object o = new S3Object(file)
    o.setKey(buildObjectKey(relativePath, filename ?: file.name))
    o.setAcl(AccessControlList.REST_CANNED_PUBLIC_READ)
    s3Service.putObjectMaybeAsMultipart(defaultBucket, o, 5242880)
  }

  void delete(String relativePath, String filename) {
    s3Service.deleteObject(defaultBucket, buildObjectKey(relativePath, filename))
    aws.s3().on(defaultBucket).delete(filename, relativePath)
  }

  private String buildObjectKey(String relativePath, String filename) {
    if (!relativePath.endsWith('/')){
		  relativePath.concat('/')
    }
    relativePath.concat(filename)
    relativePath
  }
}
