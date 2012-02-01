package mirari.model.site

import com.google.code.morphia.annotations.Index
import com.google.code.morphia.annotations.Indexes
import com.google.code.morphia.annotations.Reference
import mirari.model.Site
import mirari.model.page.PageType
import ru.mirari.infra.mongo.MorphiaDomain

/**
 * @author alari
 * @since 1/31/12 11:01 PM
 */
@Indexes([
@Index(value = "type,site", unique = true, dropDups = true)
])
class PageFeed extends MorphiaDomain {
    PageType type
    @Reference(lazy = true) Site site

    String title

    String toString() {
        title ?: type
    }
}
