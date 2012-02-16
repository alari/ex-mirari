@Typed package mirari.model.strategy.inners

import mirari.model.Unit
import mirari.vm.InnersHolderVM

/**
 * @author alari
 * @since 1/6/12 2:47 PM
 */
interface InnersHolder {
    List<Unit> getInners()

    void setInners(List<Unit> inners)

    InnersPolicy getInnersPolicy()

    void attachInner(Unit u)

    Unit getNextInnerUnit(Unit current)

    Unit getPrevInnerUnit(Unit current)

    void setInners(InnersHolderVM viewModel)

    void setInners(InnersHolderVM viewModel, Map<String, Unit> oldInners)

    void deleteInners()
}
