package mirari.model.disqus

import mirari.model.Page
import mirari.model.Site
import mirari.vm.CommentVM
import ru.mirari.infra.TextProcessUtil
import ru.mirari.infra.mongo.MorphiaDomain
import com.google.code.morphia.annotations.*
import mirari.model.digest.NoticeReason
import mirari.vm.ReasonVM
import mirari.model.digest.NoticeType
import mirari.model.digest.Notice

/**
 * @author alari
 * @since 2/1/12 8:06 PM
 */
@Indexes([
@Index("page,dateCreated"),
@Index("pagePlacedOnSites,pageDraft,-dateCreated")
])
class Comment extends MorphiaDomain implements NoticeReason {
    @Reference(lazy = true) Page page

    // Page discovery
    private boolean pageDraft = false
    @Reference(lazy = true) private List<Site> pagePlacedOnSites = []

    @Indexed
    Date dateCreated = new Date()
    Date lastUpdated

    @Reference Site owner

    String title
    String text

    @PrePersist
    void prePersist() {
        lastUpdated = new Date()
        pageDraft = page.draft
        pagePlacedOnSites = page.placedOnSites
    }

    CommentVM getViewModel() {
        CommentVM.build(this)
    }

    String getHtml() {
        TextProcessUtil.markdownToHtml(text)
    }
}
