package mirari.ko

/**
 * @author alari
 * @since 1/6/12 2:28 PM
 */
class ViewModel extends HashMap{

    String getTitle(){
        get("title")
    }

    String getId() {
        get("id")
    }

    List<UnitViewModel> getInners(){
        get("inners")
    }
}
