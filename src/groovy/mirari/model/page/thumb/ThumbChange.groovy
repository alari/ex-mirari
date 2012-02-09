package mirari.model.page.thumb

import mirari.event.Event
import mirari.event.EventListenerBean
import mirari.model.Page
import mirari.model.Unit
import mirari.model.avatar.Avatar
import mirari.model.strategy.content.ContentPolicy
import mirari.repo.AvatarRepo
import mirari.repo.PageRepo
import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired

/**
 * @author alari
 * @since 2/9/12 3:54 PM
 */
abstract class ThumbChange extends EventListenerBean {
    @Autowired PageRepo pageRepo
    @Autowired AvatarRepo avatarRepo
    private final Logger log = Logger.getLogger(this.class)

    protected Avatar getAvatar(Event event) {
        if (event.params.avatarId) {
            avatarRepo.getById((String) event.params.avatarId)
        } else {
            avatarRepo.getBasic((String) event.params.basicName)
        }
    }

    protected String getInnerImageThumbSrc(final Page page) {
        Unit u = getFirstImage(page.inners)
        if (u) {
            return u.viewModel.params.srcTiny
        }
        null
    }

    private Unit getFirstImage(List<Unit> units) {
        for (u in units) {
            if (u.contentPolicy == ContentPolicy.IMAGE) {
                return u
            }
        }
        for (u in units) {
            u = getFirstImage(u.inners)
            if (u) return u
        }
        null
    }
}
