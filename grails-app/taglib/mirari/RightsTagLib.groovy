package mirari

import mirari.model.face.RightsControllable
import mirari.model.Site

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
    
    def ifCanAdmin = {attrs, body ->
        Site site = attrs.site
        if(!site) return;
        if (rightsService.canAdmin(site)) {
            out << body()
        }
    }
}
