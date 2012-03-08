package mirari.model.digest

import ru.mirari.infra.mongo.MorphiaDomain
import com.google.code.morphia.annotations.Reference
import mirari.model.Site
import com.google.code.morphia.annotations.Indexes
import com.google.code.morphia.annotations.Index

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
    
    Date dateCreated = new Date()

    boolean watched = false
}
