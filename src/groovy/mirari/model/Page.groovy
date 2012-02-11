package mirari.model

import mirari.event.EventType
import mirari.ko.PageViewModel
import mirari.model.avatar.Avatar
import mirari.model.avatar.AvatarHolder
import mirari.model.avatar.AvatarHolderDomain
import mirari.model.avatar.DomainAvatarHolderBehaviour
import mirari.model.face.RightsControllable
import mirari.model.page.PageInnersBehaviour
import mirari.model.page.PageTaggable
import mirari.model.page.PageType
import mirari.model.page.Taggable
import mirari.model.page.thumb.ThumbOrigin
import mirari.model.strategy.inners.InnersHolder
import mirari.model.strategy.inners.InnersHolderDomain
import mirari.model.strategy.inners.InnersPolicy
import mirari.util.link.LinkAttributesFitter
import mirari.util.link.LinkUtil
import org.apache.commons.lang.RandomStringUtils
import ru.mirari.infra.mongo.MorphiaDomain
import com.google.code.morphia.annotations.*

/**
 * @author alari
 * @since 11/28/11 1:29 PM
 */
@Entity("page")
@Indexes([
@Index(value = "site,nameSorting", unique = true, dropDups=true),
@Index(value = "sites,-publishedDate,draft")
])
class Page extends MorphiaDomain implements RightsControllable, LinkAttributesFitter, AvatarHolderDomain, InnersHolderDomain {
    String getUrl(Map args = [:]) {
        args.put("for", this)
        LinkUtil.getUrl(args)
    }

    // Let the tag pages work on the order
    @Reference(lazy = true) Set<Tag> _tags = []
    @Delegate @Transient
    transient private Taggable taggableBehaviour = new PageTaggable(this)

    // Avatar behaviour
    @Reference(lazy = true) Avatar _avatar
    @Delegate @Transient
    transient private AvatarHolder avatarBehaviour = new DomainAvatarHolderBehaviour(this, EventType.PAGE_AVATAR_CHANGED)

    String getBasicAvatarName() {type.name}

    // where (site) 
    @Reference Site site

    @Indexed
    @Reference(lazy = true) Set<Site> sites = []
    // who
    @Reference Site owner
    // named after
    String name = RandomStringUtils.randomAlphanumeric(5)
    String nameSorting
    String title
    void setTitle(String t) {
        title = t
        setNameFromTitle()
    }

    // kind of
    @Indexed
    PageType type = PageType.PAGE
    // when
    Date dateCreated = new Date();
    Date lastUpdated = new Date();
    Date publishedDate

    @PrePersist
    void prePersist() {
        lastUpdated = new Date();
        if(!name) {
            if(title) {
                setNameFromTitle()
            } else {
                name = RandomStringUtils.randomAlphanumeric(5).toLowerCase()
            }
        }
        nameSorting = name.toLowerCase()
    }
    
    private void setNameFromTitle() {
        name = title.replaceAll(" ", "_").replaceAll(/[!?&*{}\[\]^\$#@~<>\\|'":;`#]/, "")
        if(name.size() < 2) name = name.concat(RandomStringUtils.randomAlphanumeric(name.size()-2 ?: 1).toLowerCase())
    }

    boolean isEmpty() {
        for (Unit u: inners) {
            if (!u.empty) return false
        }
        true
    }

    // Thumb object!
    int thumbOrigin = ThumbOrigin.TYPE_DEFAULT
    String thumbSrc

    // for RightsControllable
    boolean draft

    void setDraft(boolean d) {
        if (draft != d) {
            draft = d
            firePostPersist(EventType.PAGE_DRAFT_CHANGED, [draft: d])
        }
    }

    String toString() {
        title ?: type
    }

    // **************** View Model building

    PageViewModel getViewModel() {
        PageViewModel model = new PageViewModel(id: stringId)
        innersPolicy.strategy.attachInnersToViewModel(this, model)
        taggableBehaviour.attachTagsToViewModel(model)
        model.draft = draft
        model.avatar = getAvatar().viewModel
        model.title = title
        model.type = type.name
        model
    }

    void setViewModel(PageViewModel vm) {
        if (vm.id && stringId != vm.id) {
            throw new IllegalArgumentException("Page object must have the same id with a view model")
        }
        boolean wasDraft = getDraft()
        if (wasDraft != getDraft()) {
            firePostPersist(EventType.PAGE_DRAFT_CHANGED, [draft: getDraft()])
        }
        setInners(vm, getRestInners())
        draft = vm.draft
        taggableBehaviour.setTags(vm.tags)
        setTitle vm.title
        type = PageType.getByName(vm.type) ?: PageType.PAGE
    }

    @Override
    @Typed
    void fitLinkAttributes(Map attributes) {
        attributes.controller = attributes.controller ?: "sitePage"
        attributes.base = "http://".concat(site.host)
        ((Map) attributes.params).pageName = name ?: "null"
    }

    // inners

    @Reference(lazy = true) List<Unit> _inners = []

    @Transient
    transient final InnersPolicy _innersPolicy = InnersPolicy.ANY

    @Transient
    transient private Map<String, Unit> restInners

    Map<String, Unit> getRestInners() {
        if (restInners == null) {
            restInners = [:]
        }
        restInners
    }

    @Delegate @Transient
    private InnersHolder innersBehaviour = new PageInnersBehaviour(this)
}
