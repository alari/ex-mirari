package mirari.model.unit.inners.impl

import mirari.model.unit.inners.InnersStrategy
import mirari.model.unit.inners.InnersHolder
import mirari.model.Unit
import mirari.vm.InnersHolderVM
import mirari.model.Page
import mirari.vm.UnitVM
import org.springframework.beans.factory.annotation.Autowired
import mirari.model.unit.content.internal.CompoundContentStrategy
import mirari.model.unit.content.CompoundSchema
import mirari.model.unit.content.ContentHolder

/**
 * @author alari
 * @since 4/6/12 11:26 PM
 */
class CompoundInnersStrategy extends InnersStrategy {
    @Autowired CompoundContentStrategy compoundContentStrategy

    @Override
    void setInners(InnersHolder holder, InnersHolderVM viewModel, Page page, Map<String, Unit> oldInners) {

        final CompoundSchema schema = compoundContentStrategy.getType((ContentHolder)holder).schema

        final List<UnitVM> inners = viewModel.inners
        viewModel.inners = []
        for(UnitVM u in inners) if(schema.canAttach(viewModel, u)) {
            viewModel.inners.add(u)
        }
        super.setInners(holder, viewModel, page, oldInners)
    }

    @Override
    boolean attachInner(InnersHolder holder, Unit unit) {
        if (holder.inners == null) holder.inners = []

        // TODO: check schema!

        holder.inners.add unit
        if(holder instanceof Unit) {
            unit.outer = (Unit)holder
        }
        true
    }
}
