package mirari

import mirari.morphia.Site
import org.apache.log4j.Logger
import org.codehaus.groovy.grails.web.mapping.LinkGenerator
import mirari.morphia.Page
import mirari.morphia.Unit

class SiteLinkService {

    static transactional = false

    static final Logger log = Logger.getLogger(this)

    LinkGenerator grailsLinkGenerator

    @Typed
    String getUrl(Site site, Map args = [:]) {
        args.action = args.action ?: ""
        args.controller = args.controller ?: "site"
        args.params = args.params ?: [:]
        ((Map)args.params).siteName = site.name
        grailsLinkGenerator.link(args)
    }

    @Typed
    String getUrl(Page page, Map args = [absolute:true]) {
        args.action = args.action ?: ""
        args.controller = args.controller ?: "sitePage"
        args.params = args.params ?: [:]
        ((Map)args.params).siteName = ((Page)page).site.name.toString()
        ((Map)args.params).pageName = page.name ?: "null"
        grailsLinkGenerator.link(args)
    }

    @Typed
    String getUrl(Unit unit, Map args = [:]) {
        args.action = args.action ?: ""
        args.controller = args.controller ?: "siteUnit"
        args.params = args.params ?: [:]
        ((Map)args.params).siteName = unit.owner.name
        ((Map)args.params).id = unit.id.toString()
        grailsLinkGenerator.link(args)
    }
}
