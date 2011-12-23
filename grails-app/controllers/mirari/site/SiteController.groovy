package mirari.site

import mirari.morphia.Page
import mirari.ServiceResponse
import mirari.morphia.Avatar
import mirari.morphia.Site
import grails.plugins.springsecurity.Secured

class SiteController extends SiteUtilController {

    static defaultAction = "index"

    Page.Dao pageDao
    def avatarService
    def rightsService

    def index() {
        String pageNum = params.pageNum ?: "-0-"
        int pg = Integer.parseInt(pageNum.substring(1, pageNum.size()-1))

        Page.FeedQuery feed = pageDao.feed(currentSite, currentProfile?.id == currentSite.id).paginate(pg)

        [
                feed: feed
        ]
    }

    @Secured("ROLE_USER")
    def preferences() {
        if (hasNoRight(rightsService.canAdmin(currentSite))) return;
        []
    }

    @Secured("ROLE_USER")
    def setFeedBurner(FeedBurnerCommand cmd) {
        if (hasNoRight(rightsService.canAdmin(currentSite))) return;
        if (cmd.hasErrors()) {
            errorCode = "Invalid feedburner name: "+cmd.feedBurnerName.encodeAsHTML()
        } else {
            Site site = currentSite
            site.feedBurnerName = cmd.feedBurnerName
            siteDao.save(site)
        }
        redirect action: "preferences", params: [siteName:currentSiteName]
    }

    @Secured("ROLE_USER")
    def uploadAvatar() {
        if (hasNoRight(rightsService.canAdmin(currentSite))) return;
        if (hasNoRight(currentProfile?.id == currentSite.id)) return;

        if (request.post) {
            def f = request.getFile('avatar')
            ServiceResponse resp = avatarService.uploadSiteAvatar(f, currentSite, siteDao)
            render(
                    [thumbnail: avatarService.getUrl(currentProfile, Avatar.LARGE),
                            alertCode: resp.alertCode].encodeAsJSON())
        }
    }
}

class FeedBurnerCommand {
    String feedBurnerName

    static constraints = {
        feedBurnerName blank: true, nullable: true, matches: /^[a-zA-Z0-9]+$/
    }
}

