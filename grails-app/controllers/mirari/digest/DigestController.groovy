package mirari.digest

import mirari.UtilController
import mirari.repo.NoticeRepo
import mirari.util.ServiceResponse
import mirari.vm.NoticeVM
import mirari.model.digest.Notice
import mirari.RightsService
import grails.plugins.springsecurity.Secured

class DigestController extends UtilController{

    NoticeRepo noticeRepo
    RightsService rightsService

    @Secured("ROLE_USER")
    def index() {
        if (hasNoRight(rightsService.canAdmin(_site))) return;

        [:]
    }

    @Secured("ROLE_USER")
    def viewModel(int page) {
        if (hasNoRight(rightsService.canAdmin(_site))) return;

        renderJson(getSiteViewModel(page))
    }

    @Secured("ROLE_USER")
    def watch(String id) {
        if (hasNoRight(rightsService.canAdmin(_site))) return;

        ServiceResponse resp = new ServiceResponse()
        noticeRepo.watch(_site, id)
        renderJson resp
    }
    
    private ServiceResponse getSiteViewModel(int page) {
        ServiceResponse resp = new ServiceResponse()
        List<NoticeVM> notices = []
        for (Notice n : noticeRepo.feed(_site).paginate(page, 15)) {
            notices.add(n.viewModel)
            notices.last().canReact = rightsService.canReact(n)
        }
        resp.model(profileId: _profile.stringId, notices: notices)
    }
}
