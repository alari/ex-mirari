@Typed package mirari.ko

import groovy.json.JsonSlurper

/**
 * @author alari
 * @since 11/15/11 11:07 PM
 */
class UnitViewModel extends HashMap{
    UnitViewModel(Map args) {
        List<Map> units = (List)args.remove("units")
        putAll(args)
        this.put("units", new ArrayList<UnitViewModel>())
        for(Map m in units) {
            this.units.add new UnitViewModel(m)
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
    List<UnitViewModel> getUnits(){
        get("units")
    }
}
