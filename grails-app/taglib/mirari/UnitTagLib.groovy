package mirari

import mirari.morphia.Unit
import mirari.morphia.unit.single.ImageUnit
import mirari.util.image.ImageStorage

class UnitTagLib {
    static namespace = "unit"

    ImageStorage imageStorage
    Unit.Dao unitDao
    def spaceLinkService

    def tinyImage = {attrs ->
        ImageUnit u = attrs.for

        out << "<img src=\"${imageStorage.getUrl(u, ImageUnit.FORMAT_TINY)}\"/>"
    }

    def pageImage = {attrs ->
        ImageUnit u = attrs.for

        out << "<img src=\"${imageStorage.getUrl(u, ImageUnit.FORMAT_PAGE)}\"/>"
    }

    def link = {attrs, body ->
        attrs.for
        Unit u = attrs.remove("for")

        out << g.link(url: spaceLinkService.getUrl(attrs, u), body ? body() : u.toString())
    }

    def url = {attrs ->
        attrs.for
        Unit u = attrs.remove("for")

        out << spaceLinkService.getUrl(attrs, u)
    }
}
