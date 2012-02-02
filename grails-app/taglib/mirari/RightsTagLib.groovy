package mirari

import mirari.model.Site
import mirari.model.face.RightsControllable
import mirari.model.page.PageType
import mirari.model.Page

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
    
    def ifCanAdd = {attrs, body ->
        Site site = attrs.site
        PageType type = attrs.type
        if (rightsService.canAdd(site, type)) {
            out << body()
        }
    }

    def ifCanComment = {attrs, body ->
        Page page = attrs.page
        if(!page) return;
        if (rightsService.canComment(page)) {
            out << body()
        }
    }
}
