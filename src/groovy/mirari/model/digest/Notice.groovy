package mirari.model.digest

import ru.mirari.infra.mongo.MorphiaDomain
import com.google.code.morphia.annotations.Reference
import mirari.model.Site

/**
 * @author alari
 * @since 3/8/12 11:46 PM
 */
class Notice extends MorphiaDomain{
    @Reference(lazy=true) Site owner
    
    NoticeType type
    
    @Reference(lazy=true) NoticeReason reason
    
    Date dateCreated = new Date()

    boolean watched
}
