package mirari.model.page

import mirari.model.Page
import mirari.model.Unit
import mirari.model.unit.inners.InnersHolder
import mirari.model.unit.inners.InnersPolicy
import mirari.model.unit.inners.behaviour.InnersBehaviour

/**
 * @author alari
 * @since 2/9/12 7:53 PM
 */
class PageInnersBehaviour extends InnersBehaviour {
    private Page page

    PageInnersBehaviour(Page page) {
        this.page = page
    }

    @Override
    protected InnersHolder getHolder() {
        page
    }

    @Override
    protected Page getPage() {
        page
    }

    @Override
    List<Unit> getInners() {
        page._inners
    }

    @Override
    void setInners(List<Unit> inners) {
        page._inners = inners
    }

    @Override
    InnersPolicy getInnersPolicy() {
        page._innersPolicy
    }

}
