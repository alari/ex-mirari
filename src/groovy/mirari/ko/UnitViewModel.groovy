@Typed package mirari.ko

/**
 * @author alari
 * @since 11/15/11 11:07 PM
 */
class UnitViewModel extends ViewModel{
    String id
    String title
    String type
    Map<String,String> params
    List<UnitViewModel> contents

    String container
}
