@Typed package mirari.model

import mirari.ko.PageViewModel
import mirari.ko.ViewModel
import mirari.model.face.NamedThing
import mirari.model.face.RightsControllable
import mirari.model.strategy.inners.InnersHolder
import mirari.model.strategy.inners.InnersPolicy
import mirari.util.ApplicationContextHolder
import org.apache.commons.lang.RandomStringUtils
import org.codehaus.groovy.grails.web.mapping.LinkGenerator
import ru.mirari.infra.mongo.MorphiaDomain
import com.google.code.morphia.annotations.*
import mirari.ko.TagViewModel

import mirari.model.strategy.TagsManager
import mirari.util.LinkAttributesFitter

/**
 * @author alari
 * @since 11/28/11 1:29 PM
 */
@Entity("page")
@Indexes([
@Index("site"), @Index("-lastUpdated"), @Index("draft"),
@Index(value = "site,name", unique = true, dropDups = true),
@Index(value = "sites,-lastUpdated,draft")
])
class Page extends MorphiaDomain implements NamedThing, RightsControllable, InnersHolder, LinkAttributesFitter {
    static protected transient LinkGenerator grailsLinkGenerator

    static {
        grailsLinkGenerator = (LinkGenerator) ApplicationContextHolder.getBean("grailsLinkGenerator")
    }

    String getUrl(Map args = [:]) {
        args.put("for", this)
        grailsLinkGenerator.link(args)
    }

    transient final InnersPolicy innersPolicy = InnersPolicy.ANY

    // where (site)
    @Reference Site site

    @Indexed
    @Reference(lazy=true) Set<Site> sites = []
    // who
    @Reference Site owner
    // what
    @Reference(lazy = true) List<Unit> inners = []
    // named after
    String name = RandomStringUtils.randomAlphanumeric(5).toLowerCase()
    String title
    // permissions
    boolean draft = true
    // kind of
    @Indexed
    PageType type = PageType.PAGE
    // when
    Date dateCreated = new Date();
    Date lastUpdated = new Date();
    // and... organized
    // @Reference(lazy=true) Current current
    // Let the tag pages work on the order
    @Reference(lazy=true) List<Tag> tags = []

    @PrePersist
    void prePersist() {
        lastUpdated = new Date();
    }

    String toString() {
        title ?: type
    }

    transient private Map<String, Unit> restInners

    // **************** taggable behaviour
    public void addTag(Tag tag) {
        TagsManager.addTag(this, tag)
    }

    public void removeTag(Tag tag) {
        TagsManager.removeTag(this, tag)
    }
    
    public void setTags(List<TagViewModel> tagsVMs) {
        TagsManager.setPageTags(this, tagsVMs)
    }

    void attachTagsToViewModel(PageViewModel vm) {
        TagsManager.attachTagsToViewModel(this, vm)
    }

    // **************** View Model building
    PageViewModel getViewModel() {
        PageViewModel uvm = new PageViewModel(
                id: stringId,
                title: title,
                type: type.name,
                draft: draft
        )
        innersPolicy.strategy.attachInnersToViewModel(this, uvm)
        attachTagsToViewModel(uvm)
        uvm
    }

    void setViewModel(PageViewModel vm) {
        if(vm.id && stringId != vm.id) {
            throw new IllegalArgumentException("Page object must have the same id with a view model")
        }
        draft = vm.draft
        title = vm.title
        type = PageType.getByName(vm.type) ?: PageType.PAGE
        restInners = new HashMap<String, Unit>()
        setInners(vm, restInners)
        setTags(vm.tags)
    }

    Map<String, Unit> getRestInners() {
        restInners ?: [:]
    }

    // ********************** Inners Strategy
    @Override
    void setInners(List<Unit> inners) {
        this.inners = inners
    }

    @Override
    void attachInner(Unit u) {
        innersPolicy.strategy.attachInner(this, u)
    }

    @Override
    Unit getNextInnerUnit(Unit current) {
        innersPolicy.strategy.getNext(this, current)
    }

    @Override
    Unit getPrevInnerUnit(Unit current) {
        innersPolicy.strategy.getPrev(this, current)
    }

    @Override
    void setInners(ViewModel viewModel) {
        innersPolicy.strategy.setInners(this, viewModel)
    }

    @Override
    void setInners(ViewModel viewModel, Map<String, Unit> oldInners) {
        innersPolicy.strategy.setInners(this, viewModel, this, oldInners)
    }

    @Override
    void deleteInners() {
        innersPolicy.strategy.deleteInners(this)
    }

    @Override
    @Typed
    void fitLinkAttributes(Map attributes) {
        attributes.controller = attributes.controller ?: "sitePage"
        attributes.base = "http://".concat(site.host)
        ((Map)attributes.params).pageName = name ?: "null"
    }


}
