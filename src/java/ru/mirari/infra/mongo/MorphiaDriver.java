package ru.mirari.infra.mongo;

import com.google.code.morphia.Morphia;
import com.mongodb.Mongo;
import org.apache.log4j.Logger;
import org.codehaus.groovy.grails.commons.GrailsApplication;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.UnknownHostException;
import java.util.Map;

/**
 * @author Dmitry Kurinskiy
 * @since 08.09.11 15:04
 */
public class MorphiaDriver {
    Morphia morphia = new Morphia();
    Mongo mongo;
    final String dbName;

    @Autowired
    MorphiaDriver(GrailsApplication grailsApplication) throws UnknownHostException {
        Map config = (Map) grailsApplication.getConfig().get("mirari");
        config = (Map) config.get("infra");
        config = (Map) config.get("mongo");

        dbName = config.get("dbName").toString();

        final String host = config.get("host") == null ? "" : config.get("host").toString();
        final String username = config.get("username") == null ? "" : config.get("username").toString();
        final String password = config.get("password") == null ? "" : config.get("password").toString();
        final boolean dropDb = config.containsKey("dropDb") ? (Boolean) config.get("dropDb") : false;

        try {
            if (host != null && !host.isEmpty()) {
                mongo = new Mongo(host);
            } else {
                mongo = new Mongo();
            }
        } catch (UnknownHostException e) {
            Logger.getLogger(this.getClass()).error("You should provide valid host in mirari.infra.mongo.host " +
                    "config", e);
            throw e;
        }

        if (!username.isEmpty() || !password.isEmpty()) {
            mongo.getDB(dbName).authenticate(username, password.toCharArray());
        }

        if (dropDb) {
            System.out.println("Dropping Mongo database on startup...");
            mongo.getDB(dbName).dropDatabase();
        }
    }
}
