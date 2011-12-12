package mirari

import mirari.morphia.Site

class AvatarTagLib {
    static namespace = "avatar"

    def securityService
    def avatarService

    def large = {attrs ->
        attrs.for = attrs.for ?: securityService.profile
        if (!attrs.for) {
            log.error "Cannot get large avatar for unknown space"
        }

        String url = avatarService.getUrl(attrs.for, Site.AVA_LARGE)
        String upload = attrs.upload

        out << g.render(template: "/includes/largeAvatar", model: [url: url, upload: upload])
    }
}
