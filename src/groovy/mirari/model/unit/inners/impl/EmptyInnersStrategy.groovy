@Typed package mirari.model.unit.inners.impl

import mirari.model.unit.inners.InnersHolder
import mirari.model.unit.inners.InnersStrategy
import mirari.repo.UnitRepo
import org.springframework.beans.factory.annotation.Autowired

/**
 * @author alari
 * @since 1/6/12 2:57 PM
 */
class EmptyInnersStrategy extends InnersStrategy {
    @Autowired UnitRepo unitRepo

    @Override
    boolean attachInner(InnersHolder holder, mirari.model.Unit unit) {
        false
    }
}
