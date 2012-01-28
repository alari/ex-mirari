package mirari

import mirari.model.Page
import mirari.model.Site
import mirari.repo.PageRepo
import ru.mirari.infra.feed.FeedQuery

class RootController extends UtilController {

    PageRepo pageRepo

    def index() {
        // TODO: fix it!!!!!
        if (((Site)request._site)?.isSubSite()) {
            String pageNum = params.pageNum ?: "-0-"
            int pg = Integer.parseInt(pageNum.substring(1, pageNum.size()-1))

            Site site = request._site
            FeedQuery<Page> feed = pageRepo.feed(site, _profile == site).paginate(pg)

            render view: "/site/index", model: [
                    feed: feed
            ]
        } else {
            [feed: pageRepo.feed(_portal)]
        }
    }
    
    def robots() {
        response.contentType = "text/plain"
        render "User-agent: *\nDisallow: /x/\nDisallow: /s/"
        println "Robot: "+request.getHeader("user-agent")
    }
}
