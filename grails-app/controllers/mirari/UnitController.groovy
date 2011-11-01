package mirari

import grails.plugins.springsecurity.Secured

@Secured("ROLE_USER")
class UnitController extends SpaceUtilController {

    def add = {
        if(request.post) {
            ServiceResponse resp = new ServiceResponse()

            resp.level = AlertLevel.INFO
            resp.alertCode = "alert code"

            resp.model = params + ["x":"test"]

            if(params.title == "react") {
                resp.redirect("/")
            }

            renderJson resp
        }
    }
}
