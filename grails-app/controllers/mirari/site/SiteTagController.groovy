package mirari.site

import mirari.repo.TagRepo
import mirari.model.Tag
import ru.mirari.infra.feed.FeedQuery
import mirari.model.Page
import mirari.repo.PageRepo

class SiteTagController extends SiteUtilController{

    TagRepo tagRepo
    PageRepo pageRepo
    
    def feed(String id, int page) {
        Tag tag = tagRepo.getById(id)
        if (isNotFound(tag)) return;
        if (tag.site != _site) {
            redirect url: tag.site.getUrl(controller: controllerName, action: actionName, id: tag.stringId)
            return;
        }
        FeedQuery<Page> feedQuery = pageRepo.feed(tag, _profile == _site).paginate(page)

        [
                tag: tag,
                feed: feedQuery,
        ]
    }
}
