package mirari.model.avatar

import mirari.event.Event
import mirari.event.EventType

/**
 * @author alari
 * @since 2/9/12 3:38 PM
 */
public interface AvatarHolderDomain {
    Avatar get_avatar()

    void set_avatar(Avatar o)

    String getBasicAvatarName()

    Event firePostPersist(EventType eventType, Map<String, Object> params)
}