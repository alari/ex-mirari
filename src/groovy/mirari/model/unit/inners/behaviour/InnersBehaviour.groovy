@Typed package mirari.model.unit.inners.behaviour

import mirari.model.Page
import mirari.model.Unit
import mirari.model.unit.inners.InnersHolder
import mirari.vm.InnersHolderVM

/**
 * @author alari
 * @since 2/9/12 8:22 PM
 */
abstract class InnersBehaviour implements InnersHolder {
    abstract protected InnersHolder getHolder()

    abstract protected Page getPage()

    @Override
    void attachInner(Unit u) {
        innersPolicy.strategy.attachInner(holder, u)
    }

    @Override
    Unit getNextInnerUnit(Unit current) {
        innersPolicy.strategy.getNext(holder, current)
    }

    @Override
    Unit getPrevInnerUnit(Unit current) {
        innersPolicy.strategy.getPrev(holder, current)
    }

    @Override
    void setInners(InnersHolderVM viewModel) {
        innersPolicy.strategy.setInners(page, viewModel)
    }

    @Override
    void setInners(InnersHolderVM viewModel, Map<String, Unit> oldInners) {
        innersPolicy.strategy.setInners(holder, viewModel, page, oldInners)
    }

    @Override
    void deleteInners() {
        innersPolicy.strategy.deleteInners(holder)
    }
}
