package mirari

import mirari.repo.AvatarRepo

class RootController {
    def siteService

    AvatarRepo avatarRepo

    def robots() {
        response.contentType = "text/plain"
        render "User-agent: *\nDisallow: /x/\nDisallow: /s/"
        println "Robot: " + request.getHeader("user-agent")
    }
}
