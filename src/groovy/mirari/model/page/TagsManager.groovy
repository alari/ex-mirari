package mirari.model.page

import mirari.model.Tag
import mirari.repo.TagRepo
import mirari.util.ApplicationContextHolder
import mirari.vm.TagVM

/**
 * @author alari
 * @since 1/13/12 5:10 PM
 */
class TagsManager {
    static void addTag(Taggable page, Tag tag) {
        if (!page.tags.contains(tag)) page.tags.add(tag)
    }

    static void removeTag(Taggable page, Tag tag) {
        page.tags.remove(tag)
    }

    static void setTags(Taggable page, List<TagVM> tagsVMs) {
        Map<String, Tag> restTags = [:]
        for (Tag t: page.tags) {
            restTags.put(t.stringId, t)
        }
        page.tags.clear()
        TagRepo tagRepo = (TagRepo) ApplicationContextHolder.getBean("tagRepo")
        for (TagVM t: tagsVMs) {
            if (restTags.containsKey(t.id)) {
                page.tags.add(restTags.remove(t.id))
            } else {
                Tag tag = null
                if (t.id) {
                    tag = tagRepo.getById(t.id)
                    if (tag.site != page.tagsOwner) tag = null
                }
                if (tag == null && t.displayName) {
                    tag = tagRepo.getByDisplayNameAndSite(t.displayName, page.tagsOwner)
                }
                if (tag == null) {
                    tag = new Tag(site: page.tagsOwner)
                    tag.viewModel = t
                    tagRepo.save(tag)
                }
                page.tags.add(tag)
            }
        }
        // TODO: check rest tags
        // TODO: send events
    }
}
