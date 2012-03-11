package mirari.vm

import mirari.model.digest.Notice

/**
 * @author alari
 * @since 3/9/12 10:23 PM
 */
class NoticeVM {
    String id
    
    Date date
    
    String type

    boolean watched
    boolean getWatched(){watched}

    boolean canReact = false
    boolean getCanReact(){canReact}

    ReasonVM reason
    
    PageAnnounceVM page
    
    static NoticeVM build(final Notice notice) {
        new NoticeVM(notice)
    }

    NoticeVM(){}  
    
    private NoticeVM(final Notice notice) {
        id = notice.stringId
        date = notice.dateCreated
        type = notice.type.name
        watched = notice.watched

        reason = ReasonVM.build(notice.reason, notice)
        if(notice.page) {
            page = PageAnnounceVM.build(notice.page)
        }
    }
}

