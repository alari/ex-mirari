package mirari

import mirari.repo.FollowRepo
import mirari.model.Site

class FollowTagLib {
    static namespace = "follow"
    
    def securityService
    FollowRepo followRepo
    def rightsService
    
    def site = {attrs->
        Site follower = securityService.profile
        Site target = (Site)request?._site
        if (!follower || !target) return;
        if (!rightsService.canFollow(target)) return;
        
        boolean follows = followRepo.exists(follower, target)

        out << render(template:"/includes/follow", model: [follower: follower, target: target, exists: follows])
    }
}
