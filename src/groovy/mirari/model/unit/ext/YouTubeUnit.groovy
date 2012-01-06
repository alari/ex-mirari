@Typed package mirari.model.unit.ext

import mirari.ko.UnitViewModel
import mirari.model.Unit

/**
 * @author alari
 * @since 1/4/12 11:27 PM
 */
class YouTubeUnit extends Unit{
    String youtubeId

    void setViewModel(UnitViewModel viewModel) {
        super.setViewModel(viewModel)
        youtubeId = viewModel.youtubeId
    }

    UnitViewModel getViewModel() {
        UnitViewModel uvm = super.viewModel
        uvm.youtubeId = youtubeId
        uvm
    }
}
