package mirari.digest

import mirari.UtilController
import mirari.repo.NoticeRepo
import mirari.util.ServiceResponse
import mirari.vm.NoticeVM
import mirari.model.digest.Notice

class DigestController extends UtilController{

    NoticeRepo noticeRepo

    def index() {
        [:]
    }
    
    def viewModel() {
        renderJson(siteViewModel)
    }
    
    private ServiceResponse getSiteViewModel() {
        ServiceResponse resp = new ServiceResponse()
        List<NoticeVM> notices = []
        for (Notice n : noticeRepo.feedUnwatched(_site)) {
            notices.add(n.viewModel)
        }
        resp.model(notices: notices)
    }
}
