package mirari.model.page

import com.google.code.morphia.annotations.Embedded
import com.google.code.morphia.annotations.Reference
import com.google.code.morphia.annotations.Transient
import mirari.ko.InnersHolderViewModel
import mirari.ko.PageViewModel
import mirari.model.Page
import mirari.model.Unit
import mirari.model.strategy.inners.InnersHolder
import mirari.model.strategy.inners.InnersPolicy

/**
 * @author alari
 * @since 1/28/12 12:26 PM
 */
@Embedded
class PageBody implements InnersHolder {
    @Reference(lazy = true) List<Unit> inners = []

    @Transient
    transient final InnersPolicy innersPolicy = InnersPolicy.ANY

    @Transient
    transient private Map<String, Unit> restInners

    Map<String, Unit> getRestInners() {
        restInners ?: [:]
    }

    @Transient
    transient private Page page

    void setPage(Page page) {
        if (this.page == null) {
            this.page = page
        }
    }

    void attachToViewModel(PageViewModel pvm) {
        innersPolicy.strategy.attachInnersToViewModel(this, pvm)
    }

    void setViewModel(PageViewModel vm) {
        restInners = new HashMap<String, Unit>()
        setInners(vm, restInners)
    }

    // ********************** Inners Strategy
    @Override
    void setInners(List<Unit> inners) {
        this.inners = inners
    }

    @Override
    void attachInner(Unit u) {
        innersPolicy.strategy.attachInner(this, u)
    }

    @Override
    Unit getNextInnerUnit(Unit current) {
        innersPolicy.strategy.getNext(this, current)
    }

    @Override
    Unit getPrevInnerUnit(Unit current) {
        innersPolicy.strategy.getPrev(this, current)
    }

    @Override
    void setInners(InnersHolderViewModel viewModel) {
        innersPolicy.strategy.setInners(page, viewModel)
    }

    @Override
    void setInners(InnersHolderViewModel viewModel, Map<String, Unit> oldInners) {
        innersPolicy.strategy.setInners(this, viewModel, page, oldInners)
    }

    @Override
    void deleteInners() {
        innersPolicy.strategy.deleteInners(this)
    }
}
