package mirari

import mirari.model.avatar.Avatar

class AvatarTagLib {
    static namespace = "avatar"

    def securityService
    def avatarService

    def large = {attrs, body ->
        def holder = null
        if (attrs.for) {
            holder = attrs.for
        } else if (securityService.isLoggedIn()) {
            holder = securityService.profile
        }
        if (!holder) {
            log.error "Cannot get large avatar for unknown space"
            return;
        }

        String url = avatarService.getUrl(holder, Avatar.LARGE)
        String upload = body()

        out << g.render(template: "/includes/largeAvatar", model: [url: url, upload: upload])
    }
}
