@Typed package mirari.morphia

import com.google.code.morphia.Morphia
import com.mongodb.Mongo
import grails.util.Environment

/**
 * @author Dmitry Kurinskiy
 * @since 08.09.11 15:04
 */
class MorphiaDriver {
  Morphia morphia = new Morphia()
  Mongo mongo = new Mongo()
  String dbName = "mirari"

  MorphiaDriver(){
     if(Environment.getCurrent() == Environment.PRODUCTION) {
       mongo = new Mongo("mongodb.mirari.jelastic.com")
       mongo.getDB(dbName).authenticate("mirari", "Q5ubQTPm".toCharArray())
     }
    if(Environment.getCurrent() == Environment.TEST) {
      System.out.println("Using TESTING MorphiaDriver")
      dbName = "mirariTest"
      mongo.getDB(dbName).dropDatabase()
    }
  }
}
