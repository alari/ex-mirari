package mirari

import mirari.morphia.Space

class AvatarTagLib {
    static namespace = "avatar"

    def securityService
    def avatarService

    def large = {attrs ->
        attrs.for = attrs.for ?: securityService.person
        if (!attrs.for) {
            log.error "Cannot get large avatar for unknown space"
        }

        String url = avatarService.getUrl(attrs.for, Space.IMAGE_AVA_LARGE)
        String upload = attrs.upload

        out << g.render(template: "/includes/largeAvatar", model: [url: url, upload: upload])
    }
}
