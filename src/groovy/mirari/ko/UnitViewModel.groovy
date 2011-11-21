@Typed package mirari.ko

import groovy.json.JsonSlurper

/**
 * @author alari
 * @since 11/15/11 11:07 PM
 */
class UnitViewModel extends ViewModel{
    String id
    String title
    String type
    Map<String,String> params
    List<UnitViewModel> contents = []

    String container

    UnitViewModel(Map args) {
        id = args.id
        title = args.title
        type = args.type
        params = args.params
        container = args.container
        for(Map m in (List)args.contents) {
            contents.add new UnitViewModel(m)
        }
    }

    static UnitViewModel forString(String ko) {
        new UnitViewModel(new JsonSlurper().parseText(ko) as Map)
    }
}
