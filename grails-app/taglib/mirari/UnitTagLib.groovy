package mirari

import mirari.morphia.Unit
import mirari.morphia.unit.single.ImageUnit

class UnitTagLib {
    static namespace = "unit"

    Unit.Dao unitDao
    def spaceLinkService
    def imageStorageService

    def tinyImage = {attrs ->
        ImageUnit u = attrs.for

        out << "<img src=\"${imageStorageService.getUrl(u, ImageUnit.FORMAT_TINY)}\"/>"
    }

    def pageImage = {attrs ->
        ImageUnit u = attrs.for

        out << "<img src=\"${imageStorageService.getUrl(u, ImageUnit.FORMAT_PAGE)}\"/>"
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
