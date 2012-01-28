@Typed package mirari.ko

import groovy.json.JsonSlurper

/**
 * @author alari
 * @since 11/15/11 11:07 PM
 */
class UnitViewModel extends InnersHolderViewModel {
    UnitViewModel(Map args) {
        List<Map> units = (List) args.remove("inners")
        this.put("params", [:])
        putAll(args)
        this.put("inners", new LinkedList<UnitViewModel>())
        for (Map m in units) {
            inners.add new UnitViewModel(m)
        }
    }

    static UnitViewModel forString(String ko) {
        new UnitViewModel(new JsonSlurper().parseText(ko) as Map)
    }


    String getTitle() {
        get("title")
    }

    boolean get_destroy() {
        get("_destroy")
    }

    String getType() {
        get("type")
    }

    Map<String, String> getParams() {
        get("params")
    }
}
