package mirari

class RootController {
    def robots() {
        response.contentType = "text/plain"
        render "User-agent: *\nDisallow: /x/\nDisallow: /s/"
        println "Robot: "+request.getHeader("user-agent")
    }
}
