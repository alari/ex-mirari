package mirari.model.page

import mirari.ko.TagViewModel
import mirari.ko.ViewModel
import mirari.model.Site
import mirari.model.Tag

/**
 * @author alari
 * @since 1/28/12 11:18 AM
 */
public interface Taggable {
    Set<Tag> getTags()

    public void addTag(Tag tag)

    public void removeTag(Tag tag)

    public void setTags(List<TagViewModel> tagsVMs)

    void attachTagsToViewModel(ViewModel vm)

    Site getOwner()
}