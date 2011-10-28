package mirari

import grails.plugins.springsecurity.Secured

@Secured("ROLE_USER")
class UnitController extends SpaceUtilController{

    def add = {

    }
}
