@Typed package mirari.ko

import groovy.json.JsonSlurper
import mirari.model.Page

/**
 * @author alari
 * @since 11/28/11 3:38 PM
 */
class PageViewModel extends ViewModel{
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


    boolean isDraft() {
        get("draft")
    }

    String getType(){
        get("type")
    }
}
