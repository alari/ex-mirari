grails.servlet.version = "3.0" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
//grails.project.war.file = "target/${appName}-${appVersion}.war"

grails.project.source.level = 1.6

grails.war.resources = { stagingDir, args ->
    delete(dir: "${stagingDir}/f")
}

def gebVersion = "0.6.3"
def seleniumVersion = "2.19.0"

grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // uncomment to disable ehcache
        excludes 'grails-hibernate'
    }
    log "error" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve

    repositories {
        inherits true // Whether to inherit repository definitions from plugins
        grailsPlugins()
        grailsHome()
        grailsCentral()
        mavenCentral()

        // uncomment these to enable remote dependency resolution from public Maven repositories
        //mavenCentral()
        //mavenLocal()
        //mavenRepo "http://snapshots.repository.codehaus.org"
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        mavenRepo "http://repository.jboss.com/maven2/"

        // For groovy++
        mavenRepo 'http://groovypp.artifactoryonline.com/groovypp/libs-releases-local'

        // For Geb snapshot
        mavenRepo "https://nexus.codehaus.org/content/repositories/snapshots"

        // For Morphia
        mavenRepo "http://morphia.googlecode.com/svn/mavenrepo/"
        
        // For pegdown markdown
        mavenRepo "http://scala-tools.org/repo-releases"

        // For file storage
        mavenRepo "http://www.jets3t.org/maven2"
    }
    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.

        // markdown
        compile 'org.pegdown:pegdown:1.1.0'

        // jackson
        compile "org.codehaus.jackson:jackson-mapper-asl:1.9.5"

        runtime 'stax:stax:1.2.0'

        compile 'org.codehaus.gpars:gpars:0.12'
        compile 'org.mbte.groovypp:groovypp-all:0.9.0_1.8.2'

        compile('eu.medsea.mimeutil:mime-util:2.1.3') {
            exclude "slf4j-log4j12"
        }
        //compile "org:jaudiotagger:2.0.4-SNAPSHOT"

        test("org.seleniumhq.selenium:selenium-java:$seleniumVersion") {
            exclude "xml-apis"
        }
        test("com.opera:operadriver:0.6")

        test "org.codehaus.geb:geb-spock:$gebVersion"

        compile "org.jsoup:jsoup:1.6.2"

        compile "rome:rome:1.0"

        // Morphia
        compile 'com.google.code.morphia:morphia:0.99.1-SNAPSHOT'
        compile 'cglib:cglib-nodep:[2.1_3,)'
        compile 'com.thoughtworks.proxytoys:proxytoys:1.0'

        // File storage
        compile "net.java.dev.jets3t:jets3t:0.8.1"

    }

    plugins {
        compile ":jquery:1.7.1"
        compile ":webxml:1.4.1"
        compile(":resources:1.1.6"){
            excludes "hibernate"
        }

        build ":tomcat:$grailsVersion"

        test ":geb:$gebVersion", {
            excludes "spock", "hibernate"
        }
        test ":spock:0.6", {
            excludes "hibernate"
        }

        build(':release:2.0.0') {
            //excludes "svn", "nekohtml"
        }

        compile ":mail:1.0"

        // SECURITY
        runtime ':spring-security-core:1.2.7.3', {
            excludes "hibernate"
        }
    }
}
