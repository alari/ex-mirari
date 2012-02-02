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
import mirari.util.ApplicationContextHolder
import org.springframework.beans.factory.annotation.Autowired
import mirari.infra.CleanHtmlService
import org.pegdown.PegDownProcessor
import org.pegdown.Extensions
import mirari.ko.SiteInfoViewModel

/**
 * @author alari
 * @since 2/1/12 8:06 PM
 */
@Indexes([
        @Index("page,dateCreated")
])
class Comment extends MorphiaDomain{
    transient static private CleanHtmlService cleanHtmlService

    transient static private ThreadLocal<PegDownProcessor> processorThreadLocal = new ThreadLocal<PegDownProcessor>();

    static {
        cleanHtmlService = (CleanHtmlService)ApplicationContextHolder.getBean("cleanHtmlService")
    }

    static private PegDownProcessor getProcessor() {
        if (processorThreadLocal.get() == null) {
            processorThreadLocal.set(new PegDownProcessor(Extensions.ALL))
        }
        processorThreadLocal.get()
    }

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
        cleanHtmlService.clean processor.markdownToHtml(text)
    }

    void setViewModel(CommentViewModel viewModel) {
        if(stringId != viewModel.id) {
            throw new IllegalArgumentException("ViewModel with wrong ID")
        }
        title = viewModel.title
        text = viewModel.text
    }
}
