package mirari

import mirari.repo.NoticeRepo

class DigestTagLib {
    static namespace = "digest"

    def securityService
    NoticeRepo noticeRepo

    def count = {attrs->
        int c = noticeRepo.countUnwatched(securityService.profile)
        out << c ? "<b>${c}</b>" : "0"
    }
}
