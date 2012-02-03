package mirari.ko

import mirari.model.Site
import mirari.AvatarService
import mirari.util.ApplicationContextHolder
import mirari.model.Avatar

/**
 * @author alari
 * @since 2/2/12 4:49 PM
 */
class SiteInfoViewModel extends ViewModel{
    transient static private AvatarService avatarService

    static {
        avatarService = (AvatarService)ApplicationContextHolder.getBean("avatarService")
    }

    SiteInfoViewModel(Site site) {
        putAll(
                id: site.stringId,
                displayName: site.toString(),
                url: site.url,
                avatarFeed: avatarService.getUrl(site, Avatar.FEED),
                avatarTiny: avatarService.getUrl(site, Avatar.TINY),
        )
    }
}
