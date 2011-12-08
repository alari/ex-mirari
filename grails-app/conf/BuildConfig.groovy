grails.servlet.version = "2.5" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
//grails.project.war.file = "target/${appName}-${appVersion}.war"

grails.project.source.level = 1.6

grails.plugin.location.'mirari-infra-file' = "../mirari-infra-file"
grails.plugin.location.'mirari-infra-image' = "../mirari-infra-image"
grails.plugin.location.'mirari-infra-mongo' = "../mirari-infra-mongo"
grails.plugin.location.'mirari-infra-security' = "../mirari-infra-security"

grails.war.resources = { stagingDir, args ->
    delete(dir: "${stagingDir}/storage")
}

def gebVersion = "0.6.1"
def seleniumVersion = "2.5.0"

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

        mavenRepo 'http://groovypp.artifactoryonline.com/groovypp/libs-releases-local'

        // For Geb snapshot
        mavenRepo "https://nexus.codehaus.org/content/repositories/snapshots"
    }
    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.


        runtime 'stax:stax:1.2.0'
        //  runtime 'mysql:mysql-connector-java:5.1.16'

        compile 'org.codehaus.gpars:gpars:0.11'

        compile 'org.mbte.groovypp:groovypp-all:0.9.0_1.8.2'

        compile('eu.medsea.mimeutil:mime-util:2.1.3') {
            exclude "slf4j-log4j12"
        }

        test("org.seleniumhq.selenium:selenium-java:$seleniumVersion") {
            exclude "xml-apis"
        }
        test("com.opera:operadriver:0.6")

        test "org.codehaus.geb:geb-spock:$gebVersion"

        build('net.sourceforge.nekohtml:nekohtml:1.9.15') {
            excludes "xml-apis"
        }
    }

    plugins {
        //compile ":hibernate:$grailsVersion"
        compile ":jquery:1.7"
        compile(":resources:1.1.4"){
            excludes "hibernate"
        }

        build ":tomcat:$grailsVersion"

        runtime ':aws:1.1.9.2'

        test ":geb:$gebVersion", {
            excludes "spock", "hibernate"
        }
        test ":spock:0.6-SNAPSHOT", {
            excludes "hibernate"
        }

        build(':release:1.0.0.RC3') {
            excludes "svn", "nekohtml"
        }
    }
}
