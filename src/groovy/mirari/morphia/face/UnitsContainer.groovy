package mirari.morphia.face

import mirari.morphia.Unit

/**
 * @author alari
 * @since 12/12/11 6:45 PM
 */
public interface UnitsContainer {
    List<Unit> getInners()
    void setInners(List<Unit> inners)
    void attach(Unit unit)
}