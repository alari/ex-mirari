package mirari

import mirari.morphia.Site
import org.springframework.beans.factory.annotation.Autowired
import mirari.morphia.Site

class SiteFilters {

    @Autowired Site.Dao siteDao
    def alertsService

    def filters = {
        all(controller: 'site*', action: '*') {
            before = {
                params.site = siteDao.getByName(params.siteName)
                if(!params.site) {
                    alertsService.warning(flash, "error.siteNotFound")
                    redirect(uri: "/")
                    return false
                }
            }
            after = { Map model ->
                if (model) {
                    model.siteName = params.siteName
                    model.site = params.site
                }
            }
            afterView = { Exception e ->

            }
        }
    }
}
