package mirari.model.unit

import mirari.model.Unit
import mirari.model.strategy.inners.InnersHolder
import mirari.model.strategy.inners.behaviour.InnersBehaviour

/**
 * @author alari
 * @since 2/9/12 8:29 PM
 */
class UnitInnersBehaviour extends InnersBehaviour {
    Unit unit

    UnitInnersBehaviour(Unit unit) {
        this.unit = unit
    }

    @Override
    protected InnersHolder getHolder() {
        unit
    }

    @Override
    protected mirari.model.Page getPage() {
        unit.page
    }

    @Override
    List<mirari.model.Unit> getInners() {
        unit._inners
    }

    @Override
    void setInners(List<mirari.model.Unit> inners) {
        unit._inners = inners
    }

    @Override
    mirari.model.strategy.inners.InnersPolicy getInnersPolicy() {
        unit._innersPolicy
    }
}
