@Typed package mirari.model

import com.google.code.morphia.annotations.Index
import com.google.code.morphia.annotations.Indexed
import com.google.code.morphia.annotations.Indexes
import com.google.code.morphia.annotations.Reference
import mirari.util.link.LinkAttributesFitter
import mirari.vm.TagVM
import ru.mirari.infra.mongo.MorphiaDomain
import com.google.code.morphia.annotations.Entity

/**
 * @author alari
 * @since 12/22/11 6:22 PM
 */
@Indexes([
@Index(value = "site,displayName", unique = true, dropDups = true)
])
@Entity("page.tag")
class Tag extends MorphiaDomain implements LinkAttributesFitter {
    @Indexed
    @Reference(lazy = true) Site site

    @Indexed
    @Reference(lazy = true) Page page
    
    String displayName

    void setViewModel(TagVM viewModel) {
        if (viewModel.id && this.stringId != viewModel.id) {
            throw new IllegalArgumentException("ViewModel of tag should has the same id")
        }
        displayName = viewModel.displayName
    }

    TagVM getViewModel() {
        TagVM.build(this)
    }

    String toString() {
        displayName
    }

    @Override
    @Typed
    void fitLinkAttributes(Map attributes) {
        page.fitLinkAttributes(attributes)
    }
}
