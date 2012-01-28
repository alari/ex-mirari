package mirari.model.page

import com.google.code.morphia.annotations.Embedded
import mirari.model.face.AvatarHolder
import com.google.code.morphia.annotations.Reference
import mirari.model.Avatar
import mirari.model.Site
import com.google.code.morphia.annotations.Indexed

import org.apache.commons.lang.RandomStringUtils

import mirari.model.Tag
import com.google.code.morphia.annotations.PrePersist
import mirari.model.strategy.TagsManager
import mirari.ko.TagViewModel
import mirari.ko.ViewModel
import com.google.code.morphia.annotations.Indexes
import com.google.code.morphia.annotations.Index

import mirari.model.site.Profile
import mirari.ko.PageViewModel

/**
 * @author alari
 * @since 1/28/12 11:13 AM
 */
@Embedded
class PageHead implements AvatarHolder, Taggable{
    @Reference(lazy=true) Avatar avatar

    // where (site)
    @Reference Site site

    @Indexed
    @Reference(lazy=true) Set<Site> sites = []
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
    @Reference(lazy=true) Set<Tag> tags = []

    @PrePersist
    void prePersist() {
        println "page.head prepersist"
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
    
    void setSites(Map<String,Site> siteMap) {
        owner = (Site)siteMap.owner
        site = (Site)siteMap.site
        sites.addAll(site, owner)
        if (site instanceof Profile) {
            sites.add( ((Profile)site).portal )
        }
    }

    void attachTagsToViewModel(ViewModel vm) {
        TagsManager.attachTagsToViewModel(this, vm)
    }
}
