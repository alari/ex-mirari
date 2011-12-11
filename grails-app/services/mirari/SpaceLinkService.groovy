package mirari

import mirari.morphia.Space
import org.apache.log4j.Logger
import org.codehaus.groovy.grails.web.mapping.LinkGenerator
import mirari.morphia.Page
import mirari.morphia.Unit

class SpaceLinkService {

    static transactional = false

    static final Logger log = Logger.getLogger(this)

    LinkGenerator grailsLinkGenerator

    String getUrl(Space space, Map args = [:]) {
        args.action = args.action ?: ""
        args.controller = args.controller ?: "space"
        args.params = args.params ?: [:]
        args.params.spaceName = space.name
        grailsLinkGenerator.link(args)
    }

    String getUrl(Page page, Map args = [:]) {
        args.action = args.action ?: ""
        args.controller = args.controller ?: "spacePage"
        args.params = args.params ?: [:]
        args.params.spaceName = page.space.name
        // TODO: improve this fix
        args.params.pageName = page.name ?: "null"
        grailsLinkGenerator.link(args)
    }

    String getUrl(Unit unit, Map args = [:]) {
        args.action = args.action ?: ""
        args.controller = args.controller ?: "spaceUnit"
        args.params = args.params ?: [:]
        args.params.spaceName = unit.space.name
        args.params.id = unit.id.toString()
        grailsLinkGenerator.link(args)
    }
}
