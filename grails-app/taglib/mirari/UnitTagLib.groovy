package mirari

import mirari.model.Unit
import mirari.repo.UnitRepo
import org.codehaus.groovy.grails.web.mapping.LinkGenerator

class UnitTagLib {
    static namespace = "unit"

    UnitRepo unitRepo
    def imageStorageService

    LinkGenerator grailsLinkGenerator

    def renderPage = {attrs->
        Unit u = attrs.for
        boolean isOnly = attrs.containsKey("only") ? attrs.only : true
        if (u) out << g.render(template: "/unit-render/page-".concat(u.type), model: [viewModel: u.viewModel, unit:u, only: isOnly])
    }

    def tinyImage = {attrs ->
        Unit u = attrs.for

        // TODO: move it somewhere (it's described in ImageContentStrategy)
        String url = u.viewModel.params.srcTiny

        out << "<img src=\"${url}\"/>"
    }

    def pageImage = {attrs ->
        Unit u = attrs.for
        // TODO: remove it
        String url = u.viewModel.params.srcPage

        out << "<img src=\"${url}\"/>"
    }

    def fullImageLink = {attrs, body ->
        attrs.for
        Unit u = attrs.remove("for")
        // TODO: remove it
        attrs.url = u.viewModel.params.srcMax

        out << g.link(attrs, (body ? body() : null) ?: message(code:"unit.image.viewFull"))
    }

    def link = {attrs, body ->
        out << g.link(attrs, (body ? body() : null) ?: u.toString())
    }

    def url = {attrs ->
        out << grailsLinkGenerator.link(attrs)
    }
}
