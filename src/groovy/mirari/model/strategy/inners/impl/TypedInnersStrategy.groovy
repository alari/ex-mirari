@Typed package mirari.model.strategy.inners.impl

import mirari.model.strategy.inners.InnersHolder
import mirari.model.strategy.inners.InnersStrategy
import org.springframework.beans.factory.annotation.Autowired
import mirari.repo.UnitRepo
import mirari.ko.InnersHolderViewModel

/**
 * @author alari
 * @since 1/6/12 2:58 PM
 */
class TypedInnersStrategy extends InnersStrategy{
    @Autowired UnitRepo unitRepo

    @Override
    void attachInnersToViewModel(InnersHolder holder, InnersHolderViewModel vm) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    boolean attachInner(InnersHolder holder, mirari.model.Unit unit) {
        //To change body of implemented methods use File | Settings | File Templates.
        false
    }
}
