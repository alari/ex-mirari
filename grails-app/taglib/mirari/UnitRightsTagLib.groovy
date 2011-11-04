package mirari

import mirari.morphia.Unit

class UnitRightsTagLib {
    static namespace = "unit"

    def unitRightsService

    def ifCanEdit = {attrs,body->
        Unit unit = attrs.unit
        if(!unit) return;
        if(unitRightsService.canEdit(unit)) {
            out << body()
        }
    }

    def ifCanDelete = {attrs,body->
        Unit unit = attrs.unit
        if(!unit) return;
        if(unitRightsService.canDelete(unit)) {
            out << body()
        }
    }
}
