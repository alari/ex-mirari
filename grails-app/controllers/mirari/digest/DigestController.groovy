package mirari.digest

import mirari.UtilController
import mirari.repo.NoticeRepo

class DigestController extends UtilController{

    NoticeRepo noticeRepo

    def index() {
        render noticeRepo.feed(_site).total
    }
}
