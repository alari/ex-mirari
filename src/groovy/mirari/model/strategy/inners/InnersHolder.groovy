@Typed package mirari.model.strategy.inners

import mirari.ko.ViewModel
import mirari.model.Unit
import mirari.ko.InnersHolderViewModel

/**
 * @author alari
 * @since 1/6/12 2:47 PM
 */
interface InnersHolder {
    List<Unit> getInners()
    void setInners(List<Unit> inners)

    void attachInner(Unit u)
    Unit getNextInnerUnit(Unit current)
    Unit getPrevInnerUnit(Unit current)

    void setInners(InnersHolderViewModel viewModel)
    void setInners(InnersHolderViewModel viewModel, Map<String, Unit> oldInners)
    
    void deleteInners()
    
    InnersPolicy getInnersPolicy()
}
