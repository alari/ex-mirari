package mirari.model.page

import com.google.code.morphia.annotations.Embedded
import com.google.code.morphia.annotations.Indexed
import com.google.code.morphia.annotations.PrePersist
import com.google.code.morphia.annotations.Reference
import mirari.ko.PageViewModel
import mirari.ko.TagViewModel
import mirari.ko.ViewModel
import mirari.model.Avatar
import mirari.model.Site
import mirari.model.Tag
import mirari.model.face.AvatarHolder
import mirari.model.site.Profile
import org.apache.commons.lang.RandomStringUtils

/**
 * @author alari
 * @since 1/28/12 11:13 AM
 */
@Embedded
class PageHead implements AvatarHolder, Taggable {
    @Reference(lazy = true) Avatar avatar

    // where (site)
    @Reference Site site

    @Indexed
    @Reference(lazy = true) Set<Site> sites = []
    // who
    @Reference Site owner
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
    Date publishedDate

    // Let the tag pages work on the order
    @Reference(lazy = true) Set<Tag> tags = []

    @PrePersist
    void prePersist() {
        lastUpdated = new Date();
        name = name.toLowerCase()
    }

    void attachToViewModel(PageViewModel pvm) {
        pvm.title = title
        pvm.type = type.name
        pvm.draft = draft
        attachTagsToViewModel(pvm)
    }

    void setViewModel(PageViewModel vm) {
        draft = vm.draft
        title = vm.title
        type = PageType.getByName(vm.type) ?: PageType.PAGE
        setTags(vm.tags)
    }

    // **************** taggable behaviour
    void addTag(Tag tag) {
        TagsManager.addTag(this, tag)
    }

    void removeTag(Tag tag) {
        TagsManager.removeTag(this, tag)
    }

    void setTags(List<TagViewModel> tagsVMs) {
        TagsManager.setTags(this, tagsVMs)
    }

    void setSites(Map<String, Site> siteMap) {
        owner = (Site) siteMap.owner
        site = (Site) siteMap.site
        sites.addAll(site, owner)
        if (site instanceof Profile) {
            sites.add(((Profile) site).portal)
        }
    }

    void attachTagsToViewModel(ViewModel vm) {
        TagsManager.attachTagsToViewModel(this, vm)
    }
}
