package mirari.model.site

import com.google.code.morphia.annotations.Index
import com.google.code.morphia.annotations.Indexes
import com.google.code.morphia.annotations.Reference
import mirari.model.Site
import mirari.model.page.PageType
import ru.mirari.infra.mongo.MorphiaDomain
import com.google.code.morphia.annotations.Indexed
import mirari.model.Page
import com.google.code.morphia.annotations.Entity

/**
 * @author alari
 * @since 1/31/12 11:01 PM
 */
@Indexes([
@Index(value = "type,site", unique = true, dropDups = true)
])
@Entity("site.pagefeed")
class PageFeed extends MorphiaDomain {
    PageType type
    @Reference(lazy = true) Site site

    @Indexed
    @Reference(lazy = true) Page page

    String title

    long countDrafts = 0
    @Indexed
    long countPubs = 0
    @Indexed
    long countAll = 0

    @Indexed
    boolean forceDisplay = false

    String toString() {
        title ?: type
    }
}
