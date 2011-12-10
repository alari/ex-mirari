package mirari

import mirari.morphia.Unit
import mirari.morphia.unit.single.ImageUnit
import mirari.morphia.unit.coll.ImageCollUnit

class UnitTagLib {
    static namespace = "unit"

    Unit.Dao unitDao
    def spaceLinkService
    def imageStorageService

    def renderPage = {attrs->
        Unit u = attrs.for
        boolean isOnly = attrs.containsKey("only") ? attrs.only : true
        out << g.render(template: "/unit-render/page".concat(u.type), model: [unit:u, only: isOnly])
    }

    def tinyImage = {attrs ->
        Unit u = attrs.for
        String url
        if(u instanceof ImageUnit) {
            url = imageStorageService.getUrl(u, ImageUnit.FORMAT_TINY)
        } else if (u instanceof ImageCollUnit) {
            // TODO: improve this
            url = u.inners.size() ? imageStorageService.getUrl(u.inners?.first() as ImageUnit,
                    ImageUnit.FORMAT_TINY) : "/"
        }

        out << "<img src=\"${url}\"/>"
    }

    def pageImage = {attrs ->
        ImageUnit u = attrs.for

        out << "<img src=\"${imageStorageService.getUrl(u, ImageUnit.FORMAT_PAGE)}\"/>"
    }

    def fullImageLink = {attrs, body ->
        attrs.for
        ImageUnit u = attrs.remove("for")
        attrs.url = imageStorageService.getUrl(u, ImageUnit.FORMAT_MAX)

        out << g.link(attrs, (body ? body() : null) ?: message(code:"unit.image.viewFull"))
    }

    def link = {attrs, body ->
        attrs.for
        Unit u = attrs.remove("for")
        attrs.url = spaceLinkService.getUrl(u, attrs)

        out << g.link(attrs, (body ? body() : null) ?: u.toString())
    }

    def url = {attrs ->
        attrs.for
        Unit u = attrs.remove("for")

        out << spaceLinkService.getUrl(u, attrs)
    }
}
