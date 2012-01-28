package mirari.model.strategy

import mirari.model.Page
import mirari.model.Tag
import mirari.ko.TagViewModel
import mirari.repo.TagRepo
import mirari.util.ApplicationContextHolder
import mirari.ko.PageViewModel
import mirari.model.page.Taggable
import mirari.ko.ViewModel

/**
 * @author alari
 * @since 1/13/12 5:10 PM
 */
class TagsManager {
    static void addTag(Taggable page, Tag tag) {
        if(!page.tags.contains(tag)) page.tags.add(tag)
    }

    static void removeTag(Taggable page, Tag tag) {
        page.tags.remove(tag)
    }
    
    static void setTags(Taggable page, List<TagViewModel> tagsVMs) {
        Map<String,Tag> restTags = [:]
        for(Tag t:page.tags) {
            restTags.put(t.stringId, t)
        }
        page.tags.clear()
        TagRepo tagRepo = (TagRepo)ApplicationContextHolder.getBean("tagRepo")
        for(TagViewModel t : tagsVMs) {
            if(restTags.containsKey(t.id)) {
                page.tags.add(restTags.remove(t.id))
            } else {
                Tag tag = null
                if(t.id) {
                    tag = tagRepo.getById(t.id)
                    if(tag.site != page.owner) tag = null
                }
                if(tag == null && t.displayName) {
                    tag = tagRepo.getByDisplayNameAndSite(t.displayName, page.owner)
                }
                if(tag == null) {
                    tag = new Tag(site: page.owner)
                    tag.viewModel = t
                    tagRepo.save(tag)
                }
                page.tags.add(tag)
            }
        }
        // TODO: check rest tags
        // TODO: send events
    }
    
    static void attachTagsToViewModel(Taggable page, ViewModel viewModel) {
        viewModel.put("tags", [])
        for(Tag t : page.tags) {
            viewModel.tags.add(t.viewModel)
        }
    }
}
