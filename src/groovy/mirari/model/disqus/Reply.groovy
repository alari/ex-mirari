package mirari.model.disqus

import mirari.model.Page
import mirari.model.Site
import mirari.vm.ReplyVM
import ru.mirari.infra.TextProcessUtil
import ru.mirari.infra.mongo.MorphiaDomain
import com.google.code.morphia.annotations.*
import mirari.model.digest.NoticeReason
import mirari.vm.ReasonVM
import mirari.model.digest.NoticeType

/**
 * @author alari
 * @since 2/1/12 8:06 PM
 */
@Indexes([
@Index("pagePlacedOnSites,pageDraft,-dateCreated")
])
class Reply extends MorphiaDomain implements NoticeReason{
    @Reference(lazy = true) Page page
    @Reference(lazy = true) Comment comment

    // Page discovery
    private boolean pageDraft = false
    @Reference(lazy = true) private List<Site> pagePlacedOnSites = []

    @Indexed
    Date dateCreated = new Date()
    Date lastUpdated

    @Reference Site owner

    String text

    @PrePersist
    void prePersist() {
        lastUpdated = new Date()
        pageDraft = page.draft
        pagePlacedOnSites = page.placedOnSites
    }

    ReplyVM getViewModel() {
        ReplyVM.build(this)
    }

    String getHtml() {
        TextProcessUtil.markdownToHtml(text)
    }

    @Override
    ReasonVM getReasonViewModel(final NoticeType type) {
        ReasonVM.build(this, type)
    }
}
