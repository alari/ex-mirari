package mirari.vm

import mirari.model.Tag

/**
 * @author alari
 * @since 2/16/12 5:17 PM
 */
class TagVM {
    String id
    String displayName

    static TagVM build(final Tag tag) {
        new TagVM(tag)
    }

    private TagVM(final Tag tag) {
        id = tag.stringId
        displayName = tag.displayName
    }

    TagVM(){}
}
