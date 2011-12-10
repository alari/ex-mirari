package mirari

import mirari.morphia.RightsControllable

class RightsTagLib {
    static namespace = "rights"

    def rightsService

    def ifCanEdit = {attrs, body ->
        RightsControllable unit = attrs.unit
        if (!unit) return;
        if (rightsService.canEdit(unit)) {
            out << body()
        }
    }

    def ifCanDelete = {attrs, body ->
        RightsControllable unit = attrs.unit
        if (!unit) return;
        if (rightsService.canDelete(unit)) {
            out << body()
        }
    }
}
