package mirari.ko

/**
 * @author alari
 * @since 1/13/12 5:15 PM
 */
class TagViewModel extends ViewModel {
    TagViewModel(Map args) {
        putAll(args)
    }

    String getDisplayName() {
        get("displayName")
    }
}
