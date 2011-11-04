@Typed package mirari.util.file

import mirari.util.ConfigReader
import org.apache.log4j.Logger
import org.jets3t.service.acl.AccessControlList
import org.jets3t.service.impl.rest.httpclient.RestS3Service
import org.jets3t.service.model.S3Object
import org.jets3t.service.security.AWSCredentials
import org.springframework.beans.factory.annotation.Autowired

/**
 * @author Dmitry Kurinskiy
 * @since 21.10.11 12:52
 */
class S3FileStorage extends FileStoragePrototype {

    private final String defaultBucket
    private final String urlRoot
    private final Logger log = Logger.getLogger(S3FileStorage)
    final RestS3Service s3Service

    private final String urlRootSuffix = ".s3.amazonaws.com/"

    @Autowired
    S3FileStorage(ConfigReader configReader) {
        s3Service = new RestS3Service(
                new AWSCredentials(
                        configReader.read("grails.mirari.fileStorage.s3.accessKey").toString(),
                        configReader.read("grails.mirari.fileStorage.s3.secretKey").toString()
                )
        )
        defaultBucket = configReader.read("grails.mirari.fileStorage.s3.defaultBucket")
        urlRoot = configReader.read("grails.mirari.fileStorage.s3.urlRoot")
        if (!urlRoot) urlRoot = defaultBucket + urlRootSuffix
        if (!urlRoot.endsWith("/")) urlRoot = urlRoot.concat("/")
    }

    void store(final File file, String path, String filename, String bucket) {
        S3Object o = new S3Object(file)
        o.setKey(buildObjectKey(path, filename ?: file.name))
        o.setAcl(AccessControlList.REST_CANNED_PUBLIC_READ)
        s3Service.putObjectMaybeAsMultipart(bucket ?: defaultBucket, o, 5242880)
        log.info "Saved ${o.key} to s3 storage"
    }


    void delete(String path, String filename, String bucket) {
        s3Service.deleteObject(bucket ?: defaultBucket, buildObjectKey(path, filename))
    }


    String getUrl(String path, String filename, String bucket) {
        if (!bucket || bucket == defaultBucket) {
            return urlRoot + buildObjectKey(path, filename)
        } else {
            return bucket + urlRootSuffix + buildObjectKey(path, filename)
        }
    }

    private String buildObjectKey(String relativePath, String filename) {
        if (!relativePath.endsWith('/')) {
            relativePath = relativePath.concat('/')
        }
        relativePath.concat(filename)
    }
}
