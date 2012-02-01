// locations to search for config files that get merged into the main config
// config files can either be Java properties files or ConfigSlurper scripts

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [html: ['text/html', 'application/xhtml+xml'],
        xml: ['text/xml', 'application/xml'],
        text: 'text/plain',
        js: 'text/javascript',
        rss: 'application/rss+xml',
        atom: 'application/atom+xml',
        css: 'text/css',
        csv: 'text/csv',
        all: '*/*',
        json: ['application/json', 'text/json'],
        form: 'application/x-www-form-urlencoded',
        multipartForm: 'multipart/form-data'
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// What URL patterns should be processed by the resources plugin
grails.resources.adhoc.patterns = ['/images/*', '/css/*', '/js/*', '/plugins/*']

// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multipart requests
grails.web.disable.multipart = false

//grails.web.url.converter = 'hyphenated'

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password', 'password2']

// Set your local top-level domain for development and testing
String mainHost = "metamir.com"

// set per-environment serverURL stem for creating absolute links
environments {
    development {
        grails.logging.jul.usebridge = true
        mirari.infra.mongo.dbName = "mirari"
        //mirari.infra.mongo.dropDb = true
    }
    production {
        mainHost = "mirari.ru"

        grails.logging.jul.usebridge = false
        mirari.infra.mongo.host = "dbh70.mongolab.com:27707"
        mirari.infra.mongo.username = "mirari"
        mirari.infra.mongo.password = "Q5ubQTPm"
        mirari.infra.mongo.dbName = "mirari-test"
    }
    test {
        mirari.infra.mongo.dbName = "mirariTest"
        mirari.infra.mongo.dropDb = true
    }
}

grails.app.context = "/"
grails.serverURL = "http://".concat(mainHost)
mirari.mainPortal.host = mainHost
mirari.mainPortal.displayName = "Mirari"

// log4j configuration
log4j = {
    // Example of changing the log pattern for the default console
    // appender:
    //
    //appenders {
    //    console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
    //}

    error 'org.codehaus.groovy.grails.web.servlet',  //  controllers
            'org.codehaus.groovy.grails.web.pages', //  GSP
            'org.codehaus.groovy.grails.web.sitemesh', //  layouts
            'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
            'org.codehaus.groovy.grails.web.mapping', // URL mapping
            'org.codehaus.groovy.grails.commons', // core / classloading
            'org.codehaus.groovy.grails.plugins', // plugins
            'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
            'org.springframework',
            'org.hibernate',
            'net.sf.ehcache.hibernate'
}

// Added for the Spring Security Core plugin:
grails {
    plugins {
        springsecurity {
            auth {
                loginFormUrl = "/x/login"
                ajaxLoginFormUrl = '/x/login/auth-ajax'
            }
            apf {
                filterProcessesUrl = "/-checklogin"
                usernameParameter = "jname"
                passwordParameter = "jpwd"
            }
            logout {
                filterProcessesUrl = "/-checklogout"
            }
            adh {
                errorPage = '/x/login/denied'
                ajaxErrorPage = '/x/login/ajax-denied'
            }
            rememberMe {
                parameter = "remember_me"
                cookieName = "mirari_remember"
                key = "omnea_mirari"
            }
            userLookup {
                usernamePropertyName = "email"
                userDomainClassName = 'mirari.model.Account'
            }
            authority {
                className = 'ru.mirari.infra.security.Authority'
            }
            password {
                algorithm = 'md5'
            }
            failureHandler {
                defaultFailureUrl = "/x/login/authfail"
                ajaxAuthFailUrl = '/x/login/authfail?ajax=true'
            }
            successHandler {
                ajaxSuccessUrl = '/x/login/ajax-success'
                targetUrlParameter = 'm-redirect'
                defaultTargetUrl = "/"
                alwaysUseDefault = true
            }
        }
    }
}

mirari {
    infra {
        file {
            local {
                localRoot = "./web-app/"
                defaultBucket = "storage"
                urlRoot = "http://metamir.com/"
            }
            s3 {
                defaultBucket = "s.mirari.ru"
                accessKey = "AKIAINSHY2QZWHPJLZ5A"
                secretKey = "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"
                urlRoot = "http://s.mirari.ru/"
            }
        }
        mongo {

        }

    }
}
grails {
    mirari {
        sec {
            defaultRoleNames = ['ROLE_USER', 'ROLE_TALK']
            url {
                defaultTarget = "/"
                emailVerified = [controller: "settings"]
                passwordResetted = "/"
            }
        }
    }
}