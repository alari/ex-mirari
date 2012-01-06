@Typed package mirari.model.strategy.inners.impl

import mirari.ko.ViewModel
import mirari.model.Unit
import mirari.model.strategy.inners.InnersStrategy
import mirari.model.strategy.inners.InnersHolder

/**
 * @author alari
 * @since 1/6/12 2:52 PM
 */
class AnyInnersStrategy extends InnersStrategy{
    @Override
    void attachInnersToViewModel(InnersHolder holder, ViewModel vm) {
        vm.put("inners", [])
        for (Unit u: holder.inners) {
            vm.inners.add u.viewModel
        }
    }

    @Override
    boolean attachInner(InnersHolder holder, Unit unit) {
        if (holder.inners == null) holder.inners = []
        holder.inners.add unit
        true
    }
}
