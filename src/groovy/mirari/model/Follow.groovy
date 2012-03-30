package mirari.model

import ru.mirari.infra.mongo.MorphiaDomain
import com.google.code.morphia.annotations.Entity
import com.google.code.morphia.annotations.Reference
import com.google.code.morphia.annotations.Indexes
import com.google.code.morphia.annotations.Index
import mirari.model.Site

/**
 * @author alari
 * @since 3/31/12 1:21 AM
 */
@Entity("site.follow")
@Indexes([
        @Index(value="follower,target", unique=true, dropDups=true)
])
class Follow extends MorphiaDomain{
    @Reference(lazy=true) Site follower
    @Reference Site target
}
