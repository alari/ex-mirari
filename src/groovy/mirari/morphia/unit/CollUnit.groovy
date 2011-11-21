@Typed package mirari.morphia.unit

import mirari.morphia.Unit

/**
 * @author alari
 * @since 10/27/11 8:21 PM
 */
abstract class CollUnit extends Unit {
    transient public final String acceptType = type.substring(0, type.size()-4)

    void addUnit(Unit unit) {
        if(!unit.type.equalsIgnoreCase(acceptType)) {
            throw new IllegalArgumentException("Accepts only ${acceptType}")
        }
        super.addUnit(unit)
    }
}
