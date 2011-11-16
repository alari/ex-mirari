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
// whether to disable processing of multi part requests
grails.web.disable.multipart = false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password', 'password2']

// set per-environment serverURL stem for creating absolute links
environments {
    development {
        grails.logging.jul.usebridge = true
        grails.plugin.aws.ses.enabled = false
        grails.serverURL = "http://localhost:8080/mirari"
        grails.mirari.mongo.dbName = "mirari"
//    grails.mirari.mongo.dropDb = true
    }
    production {
        grails.logging.jul.usebridge = false
        grails.serverURL = "http://mirari.ru"
        grails.mirari.mongo.host = "mongodb.mirari.jelastic.com"
        grails.mirari.mongo.username = "mirari"
        grails.mirari.mongo.password = "Q5ubQTPm"
        grails.mirari.mongo.dbName = "mirari"
    }
    test {
        grails.plugin.aws.ses.enabled = false
        grails.serverURL = "http://localhost:8080/mirari"
        grails.mirari.mongo.dbName = "mirari"
        grails.mirari.mongo.dropDb = true
    }
}

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
                ajaxLoginFormUrl = '/x/login/authAjax'
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
                ajaxErrorPage = '/x/login/ajaxDenied'
            }
            rememberMe {
                parameter = "remember_me"
                cookieName = "mirari_remember"
                key = "omnea_mirari"
            }
            userLookup {
                usernamePropertyName = "name"
                userDomainClassName = 'mirari.morphia.space.subject.Person'
            }
            authority {
                className = 'mirari.morphia.space.subject.Role'
            }
            password {
                algorithm = 'md5'
            }
            failureHandler {
                defaultFailureUrl = "/x/login/authfail"
                ajaxAuthFailUrl = '/x/login/authfail?ajax=true'
            }
            successHandler {
                ajaxSuccessUrl = '/x/login/ajaxSuccess'
                targetUrlParameter = 'm-redirect'
                defaultTargetUrl = "/"
                alwaysUseDefault = true
            }
        }
    }
}

grails {
    plugin {
        aws {
            credentials {
                accessKey = "AKIAINSHY2QZWHPJLZ5A"
                secretKey = "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"
            }
            ses {
                from = "noreply@mirari.ru"
            }
        }
    }
}

grails {
    mirari {
        fileStorage {
            local {
                localRoot = "./web-app/"
                defaultBucket = "storage"
                urlRoot = "/mirari/"
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
        sec {
            defaultRoleNames = ['ROLE_USER', 'ROLE_TALK']
            url {
                defaultTarget = "/"
                emailVerified = [controller: "personPreferences"]
                passwordResetted = "/"
            }
        }
    }
}