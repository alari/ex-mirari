package mirari.model.site

import ru.mirari.infra.mongo.MorphiaDomain
import mirari.model.page.PageType
import mirari.model.Site
import com.google.code.morphia.annotations.Reference
import com.google.code.morphia.annotations.Indexes
import com.google.code.morphia.annotations.Index

/**
 * @author alari
 * @since 1/31/12 11:01 PM
 */
@Indexes([
        @Index(value="type,site", unique=true, dropDups=true)
])
class PageFeed extends MorphiaDomain{
    PageType type
    @Reference(lazy=true) Site site
    
    String title
    
    String toString() {
        title ?: type
    }
}
