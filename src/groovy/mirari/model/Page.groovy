@Typed package mirari.model

import mirari.ko.PageViewModel

import mirari.model.face.RightsControllable
import mirari.model.strategy.inners.InnersHolder
import mirari.model.strategy.inners.InnersPolicy
import mirari.util.ApplicationContextHolder

import org.codehaus.groovy.grails.web.mapping.LinkGenerator
import ru.mirari.infra.mongo.MorphiaDomain
import com.google.code.morphia.annotations.*

import mirari.util.LinkAttributesFitter

import mirari.ko.InnersHolderViewModel
import mirari.model.page.PageHead

import mirari.model.page.PageType
import mirari.model.page.PageBody

/**
 * @author alari
 * @since 11/28/11 1:29 PM
 */
@Entity("page")
@Indexes([
@Index(value = "head.site,head.name", unique=true),
@Index(value = "head.sites,-head.publishedDate,head.draft")
])
class Page extends MorphiaDomain implements RightsControllable, LinkAttributesFitter {
    static protected transient LinkGenerator grailsLinkGenerator

    static {
        grailsLinkGenerator = (LinkGenerator) ApplicationContextHolder.getBean("grailsLinkGenerator")
    }

    String getUrl(Map args = [:]) {
        args.put("for", this)
        grailsLinkGenerator.link(args)
    }

    @Embedded PageHead head = new PageHead()
    @Embedded private PageBody body = new PageBody()
    PageBody getBody() {
        body.page = this
        body
    }

        // for RightsControllable
        Site getOwner(){head.owner}
    boolean isDraft() {
        head.isDraft()
    }

    String toString() {
        head.title ?: head.type
    }

    // **************** View Model building
    PageViewModel getViewModel() {
        PageViewModel model = new PageViewModel(id: stringId)
        head.attachToViewModel(model)
        body.attachToViewModel(model)
        model
    }

    void setViewModel(PageViewModel vm) {
        if(vm.id && stringId != vm.id) {
            throw new IllegalArgumentException("Page object must have the same id with a view model")
        }
        head.viewModel = vm
        getBody().viewModel = vm
    }

    @Override
    @Typed
    void fitLinkAttributes(Map attributes) {
        attributes.controller = attributes.controller ?: "sitePage"
        attributes.base = "http://".concat(head.site.host)
        ((Map)attributes.params).pageName = head.name ?: "null"
    }
}
