package mirari

import mirari.repo.FollowRepo

class FollowController extends UtilController{

    def securityService
    FollowRepo followRepo
    def rightsService
    
    def toggle() {
        if (rightsService.canFollow(_site)) {
            if (followRepo.exists(securityService.profile, _site)) {
                followRepo.remove(securityService.profile, _site)
            } else {
                followRepo.add(securityService.profile, _site)
            }
        }
        redirect url: _site.url
    }
}
