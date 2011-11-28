@Typed package mirari.ko

import groovy.json.JsonSlurper

/**
 * @author alari
 * @since 11/15/11 11:07 PM
 */
class UnitViewModel extends HashMap{
    UnitViewModel(Map args) {
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
    Map<String,String> getParams() {
        get("params")
    }
    List<UnitViewModel> getInners(){
        get("inners")
    }
}
