package mirari.ko

/**
 * @author alari
 * @since 1/13/12 5:15 PM
 */
class TagViewModel extends HashMap{
    TagViewModel(Map args) {
        putAll(args)
    }
    
    String getId() {
        get("id")
    }
    
    String getDisplayName() {
        get("displayName")
    }
}
