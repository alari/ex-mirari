package mirari

import mirari.morphia.Unit
import mirari.morphia.unit.single.ImageUnit
import mirari.morphia.unit.collection.ImageCollectionUnit

class UnitTagLib {
    static namespace = "unit"

    Unit.Dao unitDao
    def spaceLinkService
    def imageStorageService

    def tinyImage = {attrs ->
        Unit u = attrs.for
        String url
        if(u instanceof ImageUnit) {
            url = imageStorageService.getUrl(u, ImageUnit.FORMAT_TINY)
        } else if (u instanceof ImageCollectionUnit) {
            // TODO: improve this
            url = u.units.size() ? imageStorageService.getUrl(u.units?.first() as ImageUnit,
                    ImageUnit.FORMAT_TINY) : "/"
        }

        out << "<img src=\"${url}\"/>"
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
