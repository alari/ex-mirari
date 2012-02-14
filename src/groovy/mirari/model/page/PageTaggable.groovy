package mirari.model.page

import mirari.ko.TagViewModel
import mirari.ko.ViewModel
import mirari.model.Page
import mirari.model.Site
import mirari.model.Tag

/**
 * @author alari
 * @since 2/9/12 2:00 PM
 */
class PageTaggable implements Taggable {
    private final Page page

    PageTaggable(final Page page) {
        this.page = page
    }

    @Override
    List<Tag> getTags() {
        page._tags
    }

    @Override
    void addTag(Tag tag) {
        if(page._tags.contains(tag)) {
            return
        }
        TagsManager.addTag(this, tag)
    }

    @Override
    void removeTag(Tag tag) {
        TagsManager.removeTag(this, tag)
    }

    @Override
    void setTags(List<TagViewModel> tagsVMs) {
        TagsManager.setTags(this, tagsVMs)
    }

    @Override
    void attachTagsToViewModel(ViewModel vm) {
        TagsManager.attachTagsToViewModel(this, vm)
    }

    @Override
    Site getTagsOwner() {
        page.owner
    }
}
