@Typed package mirari.ko

import groovy.json.JsonSlurper
import mirari.morphia.Unit
import mirari.morphia.unit.single.TextUnit
import mirari.morphia.unit.single.ImageUnit

/**
 * @author alari
 * @since 11/15/11 11:07 PM
 */
class UnitViewModel extends HashMap{
    UnitViewModel(Map args) {
        List<Map> units = (List)args.remove("inners")
        putAll(args)
        this.put("inners", new LinkedList<UnitViewModel>())
        for(Map m in units) {
            inners.add new UnitViewModel(m)
        }
    }

    static UnitViewModel forString(String ko) {
        new UnitViewModel(new JsonSlurper().parseText(ko) as Map)
    }

    void assignTo(Unit unit) {
        unit.viewModel = this
    }

    Unit toUnit() {
        if(id && !id.isEmpty()) {
            // Unit should be built
            throw new IllegalStateException("Unit should be built for: "+type)
        }
        Unit unit
        switch(type) {
            case "Text": unit = new TextUnit(); break;
            case "Image": unit = new ImageUnit(); break;
            default: throw new IllegalStateException("Unit type is unknown: "+type)
        }
        unit.viewModel = this
        unit
    }

    boolean get_destroy() {
        get("_destroy")
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
