package mirari

import mirari.util.image.ImageStorage
import mirari.morphia.Unit
import mirari.morphia.unit.single.ImageUnit

class UnitTagLib {
    static namespace = "unit"

    ImageStorage imageStorage
    Unit.Dao unitDao

    def tinyImage = {attrs->
        ImageUnit u = attrs.for

        out << "<img src=\"${imageStorage.getUrl(u, ImageUnit.FORMAT_TINY)}\"/>"
    }

    def pageImage = {attrs->
        ImageUnit u = attrs.for

        out << "<img src=\"${imageStorage.getUrl(u, ImageUnit.FORMAT_PAGE)}\"/>"
    }

    def link = {attrs, body->
        Unit u = attrs.for

        attrs.controller = attrs.controller ?: "spaceUnit"
        attrs.action = attrs.action ?: "show"
        attrs.params = [spaceName: u.space.name, unitName: u.name] + (attrs.params instanceof Map ? attrs.params : [:])

        out << g.link(attrs, body())
    }
}
