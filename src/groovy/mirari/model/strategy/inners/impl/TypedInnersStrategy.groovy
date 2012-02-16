@Typed package mirari.model.strategy.inners.impl

import mirari.model.strategy.inners.InnersHolder
import mirari.model.strategy.inners.InnersStrategy
import mirari.repo.UnitRepo
import org.springframework.beans.factory.annotation.Autowired

/**
 * @author alari
 * @since 1/6/12 2:58 PM
 */
class TypedInnersStrategy extends InnersStrategy {
    @Autowired UnitRepo unitRepo

    @Override
    boolean attachInner(InnersHolder holder, mirari.model.Unit unit) {
        false
    }
}
