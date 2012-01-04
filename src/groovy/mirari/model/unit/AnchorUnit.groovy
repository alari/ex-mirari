@Typed package mirari.model.unit

import com.google.code.morphia.annotations.Reference
import mirari.model.Unit

/**
 * @author alari
 * @since 10/27/11 8:23 PM
 */
class AnchorUnit extends Unit {
    @Reference Unit unit
}
