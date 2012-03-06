package mirari.model

import mirari.event.EventType
import mirari.model.avatar.Avatar
import mirari.model.avatar.AvatarHolder
import mirari.model.avatar.AvatarHolderDomain
import mirari.model.avatar.DomainAvatarHolderBehaviour
import mirari.model.face.RightsControllable
import mirari.model.page.PageInnersBehaviour
import mirari.model.page.PageTaggable
import mirari.model.page.PageType
import mirari.model.page.Taggable
import mirari.model.image.thumb.ThumbOrigin
import mirari.model.unit.inners.InnersHolder
import mirari.model.unit.inners.InnersHolderDomain
import mirari.model.unit.inners.InnersPolicy
import mirari.util.link.LinkAttributesFitter
import mirari.util.link.LinkUtil
import mirari.util.named.TitleNameSetter
import mirari.util.named.TitleNamedDomain
import mirari.vm.PageVM
import org.apache.commons.lang.RandomStringUtils
import ru.mirari.infra.mongo.MorphiaDomain
import com.google.code.morphia.annotations.*
import mirari.model.image.PageImage
import mirari.model.image.CommonImage

/**
 * @author alari
 * @since 11/28/11 1:29 PM
 */
@Entity("page")
@Indexes([
@Index(value = "site,nameSorting", unique = true, dropDups = true),
@Index(value = "placedOnSites,-publishedDate,draft,type")
])
class Page extends MorphiaDomain implements TitleNamedDomain, RightsControllable, LinkAttributesFitter, AvatarHolderDomain, InnersHolderDomain {
    String getUrl(Map args = [:]) {
        args.put("for", this)
        LinkUtil.getUrl(args)
    }

    // Let the tag pages work on the order
    @Reference(lazy = true) List<Tag> _tags = []
    @Delegate @Transient
    transient private Taggable taggableBehaviour = new PageTaggable(this)

    // Avatar behaviour
    @Reference(lazy = true) Avatar _avatar
    @Delegate @Transient
    transient private AvatarHolder avatarBehaviour = new DomainAvatarHolderBehaviour(this, EventType.PAGE_AVATAR_CHANGED)

    String getBasicAvatarName() {type.name}

    // where (site) 
    @Reference Site site

    void setSite(Site site) {
        this.site = site
        placeOn(site)
        if (site.isSubSite()) {
            placeOn(site.portal)
        }
    }
    // stack of sites
    @Indexed
    @Reference(lazy = true) private List<Site> placedOnSites = []

    List<Site> getPlacedOnSites() {
        placedOnSites
    }

    void placeOn(Site site) {
        if (placedOnSites.contains(site)) {
            return
        }
        placedOnSites.add(site)
        firePostPersist(EventType.PAGE_PLACED_ON_SITES_CHANGED, [add: site.stringId])
    }

    void removeFrom(Site site) {
        placedOnSites.remove(site)
        firePostPersist(EventType.PAGE_PLACED_ON_SITES_CHANGED, [remove: site.stringId])
    }

    // who
    @Reference Site owner

    void setOwner(Site owner) {
        this.owner = owner
        placeOn(owner)
    }
    // named after
    String name = RandomStringUtils.randomAlphanumeric(5)
    String nameSorting
    String title
    void setTitle(String v) {
        if(v != title) {
            title = v
            if (title && title.size() > 127) {
                title = title.substring(0, 127)
            }
            TitleNameSetter.setNameFromTitle(this)
        }
    }

    // kind of
    @Indexed
    PageType type
    // when
    Date dateCreated = new Date();
    Date lastUpdated = new Date();
    Date publishedDate

    @PrePersist
    void prePersist() {
        lastUpdated = new Date();
        nameSorting = name.toLowerCase()
        if (site == owner.portal || site.portal == owner.portal) {
            site = owner
        }
    }

    boolean isEmpty() {
        for (Unit u: inners) {
            if (!u.empty) return false
        }
        true
    }

    @Embedded PageImage image = new PageImage()
    CommonImage getNotInnerImage() {
        image.origin == ThumbOrigin.PAGE_INNER_IMAGE ? owner.avatar : image
    }

    // for RightsControllable
    boolean draft

    void setDraft(boolean d) {
        if (draft != d) {
            draft = d
            firePostPersist(EventType.PAGE_DRAFT_CHANGED, [draft: d])
        }
    }

    String toString() {
        title ?: "* * *"
    }

    // **************** View Model building

    PageVM getViewModel() {
        PageVM.build(this)
    }

    void setViewModel(PageVM vm) {
        if (vm.id && stringId != vm.id) {
            throw new IllegalArgumentException("Page object must have the same id with a view model")
        }
        boolean wasDraft = getDraft()

        draft = vm.draft

        if (isPersisted() && wasDraft != getDraft()) {
            firePostPersist(EventType.PAGE_DRAFT_CHANGED, [draft: getDraft()])
        }
        setInners(vm, getRestInners())
        draft = vm.draft
        taggableBehaviour.setTags(vm.tags)
        setTitle vm.title
        if(!type) type = PageType.getByName(vm.type) ?: PageType.PAGE
        if(!type) type = PageType.PAGE
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
