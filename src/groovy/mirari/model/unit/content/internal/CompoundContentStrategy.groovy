package mirari.model.unit.content.internal

import mirari.model.unit.content.ContentHolder
import mirari.vm.UnitVM
import mirari.model.unit.content.ContentData
import mirari.model.unit.content.CompoundType
import mirari.model.unit.inners.InnersHolder

/**
 * @author alari
 * @since 4/6/12 11:28 PM
 */
class CompoundContentStrategy extends InternalContentStrategy {

    CompoundType getType(final ContentHolder unit) {
        CompoundType.get(ContentData.COMPOUND_TYPE.getFrom(unit))
    }

    CompoundType getType(final UnitVM unit) {
        CompoundType.get(unit.params.type)
    }

    @Override
    void attachContentToViewModel(final ContentHolder unit, UnitVM unitViewModel) {
        unitViewModel.params = [type: ContentData.COMPOUND_TYPE.getFrom(unit)]
    }

    @Override
    void setViewModelContent(ContentHolder unit, final UnitVM unitViewModel) {
        ContentData.COMPOUND_TYPE.putTo(unit, unitViewModel.params.type)
    }

    @Override
    boolean isEmpty(ContentHolder unit) {
        !getType(unit).schema.containsRequired((InnersHolder)unit)
    }
}
