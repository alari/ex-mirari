@Typed package mirari.model.unit

import mirari.model.Unit

/**
 * @author alari
 * @since 10/27/11 8:21 PM
 */
abstract class CollUnit extends Unit {
    transient public final String acceptType = type.substring(0, type.size() - 4)

    void attach(Unit unit) {
        if (!unit.type.equalsIgnoreCase(acceptType)) {
            throw new IllegalArgumentException("Accepts only ${acceptType}")
        }
        super.attach(unit)
    }
}
