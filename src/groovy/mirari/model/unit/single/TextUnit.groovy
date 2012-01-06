@Typed package mirari.model.unit.single

import com.google.code.morphia.annotations.Entity
import com.google.code.morphia.annotations.Reference
import mirari.ko.UnitViewModel
import mirari.model.unit.SingleUnit
import ru.mirari.infra.mongo.Domain
import mirari.model.unit.UnitContent

/**
 * @author alari
 * @since 11/22/11 9:20 PM
 */
class TextUnit extends SingleUnit {
    void setViewModel(UnitViewModel viewModel) {
        super.setViewModel(viewModel)
        if (!content) content = new UnitContent()
        content.text = viewModel.text
    }

    UnitViewModel getViewModel() {
        UnitViewModel uvm = super.viewModel
        uvm.text = content.text
        uvm
    }
}
