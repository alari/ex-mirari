@Typed package mirari.model.unit.ext

import mirari.ko.UnitViewModel
import mirari.model.Unit

/**
 * @author alari
 * @since 1/5/12 12:32 AM
 */
class RussiaRuUnit extends Unit{
    String videoId

    void setViewModel(UnitViewModel viewModel) {
        super.setViewModel(viewModel)
        videoId = viewModel.videoId
    }

    UnitViewModel getViewModel() {
        UnitViewModel uvm = super.viewModel
        uvm.youtubeId = videoId
        uvm
    }
}
