@Typed package mirari.morphia.unit

import com.google.code.morphia.annotations.Reference
import mirari.morphia.Unit

/**
 * @author alari
 * @since 10/27/11 8:23 PM
 */
class AnchorUnit extends Unit {
    @Reference Unit unit
}
