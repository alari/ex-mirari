package mirari.model.digest

import ru.mirari.infra.mongo.MorphiaDomain
import com.google.code.morphia.annotations.Reference
import mirari.model.Site
import com.google.code.morphia.annotations.Indexes
import com.google.code.morphia.annotations.Index
import mirari.vm.NoticeVM
import mirari.model.Page

/**
 * @author alari
 * @since 3/8/12 11:46 PM
 */
@Indexes([
        @Index(value="owner,watched,-dateCreated")
])
class Notice extends MorphiaDomain{
    @Reference(lazy=true) Site owner
    
    NoticeType type
    
    @Reference NoticeReason reason
    @Reference Page page
    
    Date dateCreated = new Date()

    boolean watched = false

    void setReason(final NoticeReason reason) {
        if(type) {
            if(!type.reasonType.isInstance(reason)) {
                println "Wrong reason type!"
            }
        }
        this.reason = reason
    }

    void setType(NoticeType type) {
        this.type = type
        if(reason) {
            if(!type.reasonType.isInstance(reason)) {
                println "Wrong reason type!"
            }
        }
    }
    
    NoticeVM getViewModel() {
        NoticeVM.build(this)
    }
}
