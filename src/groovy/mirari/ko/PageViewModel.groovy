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
        List<Map> tags = (List)args.remove("tags")
        putAll(args)
        this.put("inners", new LinkedList<UnitViewModel>())
        this.put("tags", new LinkedList<TagViewModel>())
        for(Map m in units) {
            inners.add new UnitViewModel(m)
        }
        for (Map t : tags) {
            getTags().add new TagViewModel(t)
        }
    }

    static PageViewModel forString(String ko) {
        new PageViewModel(new JsonSlurper().parseText(ko) as Map)
    }
    
    List<TagViewModel> getTags() {
        get("tags")
    }

    boolean isDraft() {
        get("draft")
    }

    String getType(){
        get("type")
    }
}
