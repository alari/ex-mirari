@Typed package mirari.ko

import groovy.json.JsonSlurper
import mirari.morphia.Page

/**
 * @author alari
 * @since 11/28/11 3:38 PM
 */
class PageViewModel extends HashMap{
    PageViewModel(Map args) {
        List<Map> units = (List)args.remove("inners")
        putAll(args)
        this.put("inners", new LinkedList<UnitViewModel>())
        for(Map m in units) {
            inners.add new UnitViewModel(m)
        }
    }

    static PageViewModel forString(String ko) {
        new PageViewModel(new JsonSlurper().parseText(ko) as Map)
    }

    void assignTo(Page page) {
        if(id && page.id.toString() != id) {
            throw new IllegalArgumentException("Page object must have the same id with a view model")
        }
        page.draft = draft
        page.title = title
        page.type = type
    }

    String getId() {
        get("id")
    }

    boolean isDraft() {
        get("draft")
    }
    String getTitle(){
        get("title")
    }
    String getType(){
        get("type")
    }
    List<UnitViewModel> getInners(){
        get("inners")
    }
}