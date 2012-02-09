@Typed package mirari.ko

import groovy.json.JsonSlurper

/**
 * @author alari
 * @since 11/28/11 3:38 PM
 */
class PageViewModel extends InnersHolderViewModel {
    PageViewModel(Map args) {
        List<Map> units = (List) args.remove("inners")
        List<Map> tags = (List) args.remove("tags")
        putAll(args)
        this.put("inners", new LinkedList<UnitViewModel>())
        this.put("tags", new LinkedList<TagViewModel>())
        for (Map m in units) {
            inners.add new UnitViewModel(m)
        }
        for (Map t: tags) {
            getTags().add new TagViewModel(t)
        }
    }

    static PageViewModel forString(String ko) {
        new PageViewModel(new JsonSlurper().parseText(ko) as Map)
    }

    List<TagViewModel> getTags() {
        get("tags")
    }


    String getTitle() {
        get("title")
    }

    void setTitle(String t) {
        put("title", t)
    }

    boolean isDraft() {
        get("draft")
    }

    void setDraft(boolean d) {
        put("draft", d)
    }

    String getType() {
        get("type")
    }

    void setType(String t) {
        put("type", t)
    }
}
