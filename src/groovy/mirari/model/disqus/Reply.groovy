package mirari.model.disqus

import ru.mirari.infra.mongo.MorphiaDomain
import com.google.code.morphia.annotations.Reference
import mirari.model.Page
import com.google.code.morphia.annotations.Indexed
import mirari.model.Site
import com.google.code.morphia.annotations.PrePersist
import mirari.ko.SiteInfoViewModel
import ru.mirari.infra.TextProcessUtil
import mirari.ko.ReplyViewModel

/**
 * @author alari
 * @since 2/1/12 8:06 PM
 */
class Reply extends MorphiaDomain{
    @Reference(lazy=true) Page page
    @Reference(lazy=true) Comment comment

    @Indexed
    Date dateCreated = new Date()
    Date lastUpdated

    @Reference Site owner

    String text

    @PrePersist
    void prePersist() {
        lastUpdated = new Date()
    }

    ReplyViewModel getViewModel() {
        new ReplyViewModel(
                id: stringId,
                dateCreated: dateCreated.toLocaleString(),
                text: text,
                html: html,
                owner: new SiteInfoViewModel(owner)
        )
    }

    private String getHtml() {
        TextProcessUtil.markdownToHtml(text)
    }

    void setViewModel(ReplyViewModel viewModel) {
        if(stringId != viewModel.id) {
            throw new IllegalArgumentException("ViewModel with wrong ID")
        }
        text = viewModel.text
    }
}
