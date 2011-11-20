package mirari

import mirari.morphia.Space
import mirari.morphia.Unit
import org.apache.log4j.Logger
import org.codehaus.groovy.grails.web.mapping.LinkGenerator

class SpaceLinkService {

    static transactional = false

    static final Logger log = Logger.getLogger(this)

    LinkGenerator grailsLinkGenerator

    String getUrl(Map args = [:], Space space) {
        args.action = args.action ?: ""
        args.controller = args.controller ?: "space"
        args.params = args.params ?: [:]
        args.params.spaceName = space.name
        grailsLinkGenerator.link(args)
    }

    String getUrl(Map args = [:], Unit unit) {
        args.action = args.action ?: ""
        args.controller = args.controller ?: "spaceUnit"
        args.params = args.params ?: [:]
        args.params.spaceName = unit.space.name
        // TODO: improve this fix
        args.params.unitName = unit.name ?: "null"
        grailsLinkGenerator.link(args)
    }
}
