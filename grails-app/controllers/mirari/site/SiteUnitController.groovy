package mirari.site

import mirari.UtilController
import mirari.repo.UnitRepo
import mirari.model.unit.content.internal.FeedContentStrategy
import mirari.model.Unit
import ru.mirari.infra.feed.FeedQuery
import mirari.model.Page

class SiteUnitController extends UtilController {

    static defaultAction = "index"
    
    UnitRepo unitRepo
    FeedContentStrategy feedContentStrategy
    
    def index(String id) {
        Unit u = unitRepo.getById(id)
        if (isNotFound(u)) return;
        
        if (u.type == "feed") {
            feed(id, 0)
        } else {
            render id
        }
    }
    
    def feed(String id, int page) {
        Unit u = unitRepo.getById(id)
        if (isNotFound(u)) return;
        
        FeedQuery<Page> feed = feedContentStrategy.feed(u)
        if (feed) {
            render template: "/pages-feed/links", model: [feed: feed]
        } else {
            render id
        }
    }
}
