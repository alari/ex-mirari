@Typed package mirari.ko

import groovy.json.JsonSlurper
import mirari.model.Unit

/**
 * @author alari
 * @since 11/15/11 11:07 PM
 */
class UnitViewModel extends ViewModel{
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

    boolean get_destroy() {
        get("_destroy")
    }

    String getType(){
        get("type")
    }
    Map<String,String> getParams() {
        get("params")
    }
}
