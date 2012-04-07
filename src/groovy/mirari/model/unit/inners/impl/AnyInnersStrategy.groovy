@Typed package mirari.model.unit.inners.impl

import mirari.model.Unit
import mirari.model.unit.inners.InnersHolder
import mirari.model.unit.inners.InnersStrategy
import mirari.repo.UnitRepo
import org.springframework.beans.factory.annotation.Autowired

/**
 * @author alari
 * @since 1/6/12 2:52 PM
 */
class AnyInnersStrategy extends InnersStrategy {
    @Override
    boolean attachInner(InnersHolder holder, Unit unit) {
        if (holder.inners == null) holder.inners = []
        holder.inners.add unit
        if(holder instanceof Unit) {
            unit.outer = (Unit)holder
        }
        true
    }
}
