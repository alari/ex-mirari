package mirari

import mirari.morphia.Space
import mirari.morphia.Unit
import org.codehaus.groovy.grails.web.mapping.LinkGenerator

class SpaceLinkService {

    static transactional = false

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
        args.params.unitName = unit.name
        grailsLinkGenerator.link(args)
    }
}
