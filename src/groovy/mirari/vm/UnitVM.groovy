package mirari.vm

import mirari.model.Unit

/**
 * @author alari
 * @since 2/16/12 5:17 PM
 */
class UnitVM extends InnersHolderVM {
    String id

    String type
    String title

    Date dateCreated
    Date lastUpdated

    String url

    OwnerVM owner

    String outerId

    CommonImageVM image

    Map<String, String> params = [:]

    boolean _destroy
    boolean get_destroy() {
        _destroy
    }

    static UnitVM build(final Unit unit) {
        new UnitVM(unit)
    }

    private UnitVM(final Unit unit) {
        id = unit.stringId

        type = unit.type
        title = unit.title

        dateCreated = unit.dateCreated
        lastUpdated = unit.lastUpdated

        url = unit.url

        owner = OwnerVM.build(unit.owner)

        // TODO: it's never got set
        outerId = unit.outer?.stringId

        inners = unit.inners*.viewModel

        unit.contentPolicy.strategy.attachContentToViewModel(unit, this)
    }

    UnitVM(){}
}
