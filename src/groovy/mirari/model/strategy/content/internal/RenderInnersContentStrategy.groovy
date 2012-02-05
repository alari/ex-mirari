package mirari.model.strategy.content.internal

import mirari.ko.UnitViewModel
import mirari.model.strategy.content.ContentData
import mirari.model.strategy.content.ContentHolder
import ru.mirari.infra.file.FileInfo

/**
 * @author alari
 * @since 2/3/12 4:42 PM
 */
class RenderInnersContentStrategy extends InternalContentStrategy {
    @Override
    void attachContentToViewModel(ContentHolder unit, UnitViewModel unitViewModel) {
        unitViewModel.params.renderStyle = ContentData.RENDER_STYLE.getFrom(unit)
    }

    @Override
    void setViewModelContent(ContentHolder unit, UnitViewModel unitViewModel) {
        ContentData.RENDER_STYLE.putTo(unit, unitViewModel.params.renderStyle)
    }

    @Override
    void setContentFile(ContentHolder unit, FileInfo fileInfo) {
    }

    @Override
    boolean isContentFileSupported(FileInfo type) {
        return false
    }

    @Override
    void saveContent(ContentHolder unit) {
    }

    @Override
    void deleteContent(ContentHolder unit) {
    }
}
