@Typed package mirari.morphia

import com.google.code.morphia.Morphia
import com.mongodb.Mongo
import grails.util.Environment
import org.springframework.beans.factory.annotation.Autowired
import mirari.util.ConfigReader

/**
 * @author Dmitry Kurinskiy
 * @since 08.09.11 15:04
 */
class MorphiaDriver {
  Morphia morphia = new Morphia()
  Mongo mongo
  final String dbName

  @Autowired
  MorphiaDriver(ConfigReader configReader) {
    String mongoHost = configReader.read("grails.mirari.mongo.host")
    mongo = mongoHost ? new Mongo(mongoHost) : new Mongo()

    String username = configReader.read("grails.mirari.mongo.username")
    String password = configReader.read("grails.mirari.mongo.password")

    dbName = configReader.read("grails.mirari.mongo.dbName")

    if(username || password) {
      mongo.getDB(dbName).authenticate(username, password.toCharArray())
    }

    if(configReader.read("grails.mirari.mongo.dropDb").equals(true)) {
      System.out.println "Deleting Mongo database on startup..."
      mongo.getDB(dbName).dropDatabase()
    }
  }
}
