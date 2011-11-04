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

    def link = {attrs, body->
        Unit u = attrs.for

        out << g.link(controller: "spaceUnit", action: "show", params: [spaceName: u.space.name, unitName: u.name],
                body())
    }
}
