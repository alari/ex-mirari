@Typed package mirari.model.strategy.content.external

import mirari.ko.UnitViewModel
import mirari.model.strategy.content.ContentData
import mirari.model.strategy.content.ContentHolder
import mirari.model.strategy.content.ContentStrategy
import ru.mirari.infra.file.FileInfo

/**
 * @author alari
 * @since 1/6/12 6:47 PM
 */
abstract class ExternalContentStrategy extends ContentStrategy {
    @Override
    void attachContentToViewModel(ContentHolder unit, UnitViewModel unitViewModel) {
        unitViewModel.put("params", ["externalId": getExternalId(unit)])
    }

    @Override
    void setViewModelContent(ContentHolder unit, UnitViewModel unitViewModel) {
        if (unitViewModel.params?.externalUrl && isUrlSupported(unitViewModel.params.externalUrl)) {
            buildContentByUrl(unit, unitViewModel.params.externalUrl)
        }
    }

    @Override
    boolean isExternal() {
        true
    }

    @Override
    void deleteContent(ContentHolder unit) {
        void
    }

    @Override
    void setContentFile(ContentHolder unit, FileInfo fileInfo) {
        void
    }

    @Override
    boolean isContentFileSupported(FileInfo info) {
        false
    }

    @Override
    void saveContent(ContentHolder unit) {
        void
    }

    void setExternalId(ContentHolder unit, String id) {
        ContentData.EXTERNAL_ID.putTo(unit, id)
    }

    String getExternalId(ContentHolder unit) {
        ContentData.EXTERNAL_ID.getFrom(unit)
    }
}
