package mirari.ko

import groovy.json.JsonSlurper

/**
 * @author alari
 * @since 11/28/11 3:38 PM
 */
class PageViewModel extends HashMap{
    PageViewModel(Map args) {
        List<Map> units = (List)args.remove("inners")
        putAll(args)
        this.put("inners", new ArrayList<UnitViewModel>())
        for(Map m in units) {
            inners.add new UnitViewModel(m)
        }
    }

    static UnitViewModel forString(String ko) {
        new UnitViewModel(new JsonSlurper().parseText(ko) as Map)
    }

    String getId() {
        get("id")
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
