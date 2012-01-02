@Typed package mirari.morphia.site

import mirari.morphia.Site

/**
 * @author alari
 * @since 1/2/12 3:16 PM
 */
class Portal extends Site{
    void setName(String n) {
        super.setName(n)
        host = name
    }
}
