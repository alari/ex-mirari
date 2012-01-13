@Typed package mirari.model.strategy.inners

import mirari.ko.UnitViewModel
import mirari.ko.ViewModel
import mirari.model.Page
import mirari.model.Unit
import mirari.repo.UnitRepo
import mirari.util.ApplicationContextHolder
import org.springframework.beans.factory.annotation.Autowired

/**
 * @author alari
 * @since 1/6/12 2:27 PM
 */
abstract class InnersStrategy {
    abstract protected UnitRepo getUnitRepo()

    abstract void attachInnersToViewModel(InnersHolder holder, ViewModel vm)
    
    abstract boolean attachInner(InnersHolder holder, Unit unit)
    
    Unit getNext(InnersHolder holder, Unit unit) {
        int i
        final Unit next
        for (i = 0; i < holder.inners.size(); i++) {
            if (unit.stringId == holder.inners[i].stringId) {
                next = (Unit) (i == holder.inners.size() - 1 ? holder.inners.first() : holder.inners[i + 1])
            }
        }
        next ?: unit
    }
    
    Unit getPrev(InnersHolder holder, Unit unit) {
        int i
        final Unit prev
        for (i = holder.inners.size()-1; i >= 0; --i) {
            if (unit.stringId == holder.inners[i].stringId) {
                prev = (Unit) (i == 0 ? holder.inners.last() : holder.inners[i - 1])
            }
        }
        prev ?: unit
    }

    Map<String, Unit> collectInners(InnersHolder outer) {
        Map<String, Unit> units = [:]
        if(outer.inners && outer.inners.size()) for(Unit u : outer.inners) {
            units.put(u.stringId, u)
            units.putAll(collectInners(u))
        }
        units
    }

    void setInners(Page outer, ViewModel viewModel) {
        setInners(outer, viewModel, outer)
    }

    void setInners(InnersHolder holder, ViewModel viewModel, Page page) {
        setInners(holder, viewModel, page, collectInners(holder))
    }

    void setInners(InnersHolder holder, ViewModel viewModel, Page page, Map<String, Unit> oldInners) {
        if(oldInners == null || oldInners.empty) {
            oldInners = collectInners(holder)
        }

        holder.inners = []
        for(UnitViewModel uvm in viewModel.inners) {
            Unit u
            if(uvm.id && oldInners.containsKey(uvm.id)) {
                if(uvm._destroy) {
                    continue
                }
                u = oldInners.remove(uvm.id)
            } else {
                u = unitRepo.buildFor(uvm, page)
            }
            u.viewModel = uvm
            // Todo: external units must be asserted via anchors
            u.page = page
            holder.attachInner u
            u.setInners(uvm, oldInners)
        }
        oldInners
    }
    
    void deleteInners(InnersHolder holder) {
        if(holder.inners) for(Unit u : holder.inners) {
            // TODO: find links for this unit, clear them or do something
            unitRepo.delete(u)
        }
    }
}