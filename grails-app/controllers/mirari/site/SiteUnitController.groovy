package mirari.site

import mirari.UtilController
import mirari.repo.UnitRepo
import mirari.model.unit.content.internal.FeedContentStrategy
import mirari.model.Unit
import ru.mirari.infra.feed.FeedQuery
import mirari.model.Page
import mirari.RightsService
import mirari.util.ServiceResponse
import mirari.vm.PageAnnounceVM
import mirari.model.page.PageType

class SiteUnitController extends UtilController {

    static defaultAction = "index"
    
    UnitRepo unitRepo
    FeedContentStrategy feedContentStrategy
    RightsService rightsService
    
    def index(String id) {
        Unit u = unitRepo.getById(id)
        if (isNotFound(u)) return;
        if(hasNoRight(rightsService.canView(u))) return;
        
        if (u.type == "feed") {
            feed(id, 0)
        } else {
            render id
        }
    }
    
    def feed(String id, int page) {
        Unit u = unitRepo.getById(id)
        if (isNotFound(u)) return;
        if(hasNoRight(rightsService.canView(u))) return;
        
        FeedQuery<Page> feed = feedContentStrategy.feed(u)
        if (feed) {
            render template: "/pages-feed/links", model: [feed: feed]
        } else {
            render id
        }
    }
    
    def draftsViewModel(String id, int page) {
        Unit u = unitRepo.getById(id)
        if (isNotFound(u)) {
            return
        }
        
        ServiceResponse resp = new ServiceResponse().model(typesModel)

        if(!rightsService.canSeeDrafts(u.owner)){
            renderJson(resp)
            return
        }

        FeedQuery<Page> feed = feedContentStrategy.drafts(u)
        
        List<PageAnnounceVM> pages = []
        for (Page p : feed) {
            pages.add PageAnnounceVM.build(p)
        }
        renderJson resp.model(pages: pages)
    }
    
    def feedViewModel(String id, int page) {
        Unit u = unitRepo.getById(id)
        if (isNotFound(u)) return;
        if(hasNoRight(rightsService.canView(u))) return;

        ServiceResponse resp = new ServiceResponse()

        resp.model typesModel
        
        FeedQuery<Page> feedQuery = feedContentStrategy.feed(u)
        
        if (feedQuery) {
            final int perPage = feedContentStrategy.getPerPage(u)
            feedQuery.paginate(page, perPage)

            Iterator<Page> feed = feedQuery.iterator()
            
            final String lastStyle = feedContentStrategy.getLastStyle(u) 
            if (page == 0 && lastStyle != feedContentStrategy.STYLE_NONE) {
                final Page last = feed.next()
                // we render last in html if it's full
                if (feedContentStrategy.isAnnounceStyle(lastStyle)) {
                    resp.model last: PageAnnounceVM.build(last), lastStyle: lastStyle
                }
            }
            
            List<PageAnnounceVM> announces = []
            for (Page p in feed) {
                announces.add( PageAnnounceVM.build(p) )
            }
            
            resp.model(pages: announces)
        } else {
            resp.error("not a feed")
        }
        
        renderJson(resp)
    }
    
    private Map<String,Map<String,String>> getTypesModel() {
        Map<String, String> types = [:]
        for (PageType t : PageType.values()) {
            types.put(t.name, message(code: "pageType."+t.name))
        }
        [types: types]
    }
}
