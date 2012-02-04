package mirari.model.disqus

import mirari.model.Page
import mirari.model.Site
import com.google.code.morphia.annotations.Reference
import com.google.code.morphia.annotations.PrePersist
import ru.mirari.infra.mongo.MorphiaDomain
import mirari.ko.CommentViewModel
import com.google.code.morphia.annotations.Indexes
import com.google.code.morphia.annotations.Index
import com.google.code.morphia.annotations.Indexed
import mirari.ko.SiteInfoViewModel
import ru.mirari.infra.TextProcessUtil

/**
 * @author alari
 * @since 2/1/12 8:06 PM
 */
@Indexes([
        @Index("page,dateCreated")
])
class Comment extends MorphiaDomain{
    @Reference(lazy=true) Page page

    @Indexed
    Date dateCreated = new Date()
    Date lastUpdated
    
    @Reference Site owner
    
    String title
    String text

    @PrePersist
    void prePersist() {
        lastUpdated = new Date()
    }

    CommentViewModel getViewModel() {
        new CommentViewModel(
                id: stringId,
                dateCreated: dateCreated.toLocaleString(),
                title: title,
                text: text,
                html: html,
                owner: new SiteInfoViewModel(owner)
        )
    }

    private String getHtml() {
        TextProcessUtil.markdownToHtml(text)
    }

    void setViewModel(CommentViewModel viewModel) {
        if(stringId != viewModel.id) {
            throw new IllegalArgumentException("ViewModel with wrong ID")
        }
        title = viewModel.title
        text = viewModel.text
    }
}