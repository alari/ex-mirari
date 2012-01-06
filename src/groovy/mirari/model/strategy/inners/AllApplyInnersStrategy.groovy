package mirari.model.strategy.inners

import mirari.ko.ViewModel
import mirari.model.Unit

/**
 * @author alari
 * @since 1/6/12 2:52 PM
 */
class AllApplyInnersStrategy extends InnersStrategy{
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
