package mirari.model.page

import mirari.model.Site
import mirari.model.Tag
import mirari.vm.TagVM

/**
 * @author alari
 * @since 1/28/12 11:18 AM
 */
public interface Taggable {
    List<Tag> getTags()

    public void addTag(Tag tag)

    public void removeTag(Tag tag)

    public void setTags(List<TagVM> tagsVMs)

    Site getTagsOwner()
}