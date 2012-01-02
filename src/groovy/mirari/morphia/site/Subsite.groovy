@Typed package mirari.morphia.site

import mirari.morphia.Site
import com.google.code.morphia.annotations.Indexed

/**
 * @author alari
 * @since 1/2/12 11:38 PM
 */
abstract class Subsite extends Site{
    @Indexed
    Portal portal

    void setName(String n) {
        super.setName(n)
        host = name + "." + portal.host
    }
}
