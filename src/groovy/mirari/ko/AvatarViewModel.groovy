package mirari.ko

/**
 * @author alari
 * @since 1/20/12 4:09 PM
 */
class AvatarViewModel extends ViewModel{
    AvatarViewModel(Map args) {
        putAll(args)
    }

    String getSrcLarge() {
        this.get("srcLarge")
    }
    
    String getSrcTiny() {
        this.get("srcTiny")
    }
    
    String getSrcFeed() {
        this.get("srcFeed")
    }
}
