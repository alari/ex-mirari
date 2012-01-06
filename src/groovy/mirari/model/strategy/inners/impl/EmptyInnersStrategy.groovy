@Typed package mirari.model.strategy.inners.impl

import mirari.model.strategy.inners.InnersStrategy
import mirari.model.strategy.inners.InnersHolder

/**
 * @author alari
 * @since 1/6/12 2:57 PM
 */
class EmptyInnersStrategy extends InnersStrategy{
    @Override
    void attachInnersToViewModel(InnersHolder holder, mirari.ko.ViewModel vm) {
    }

    @Override
    boolean attachInner(InnersHolder holder, mirari.model.Unit unit) {
        false
    }
}
