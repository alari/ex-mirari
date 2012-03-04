@Typed package mirari.model.unit.content.external

import mirari.model.unit.content.ContentData
import mirari.model.unit.content.ContentHolder
import mirari.model.unit.content.ContentStrategy
import mirari.vm.UnitVM
import ru.mirari.infra.file.FileInfo

/**
 * @author alari
 * @since 1/6/12 6:47 PM
 */
abstract class ExternalContentStrategy extends ContentStrategy {
    @Override
    void attachContentToViewModel(ContentHolder unit, UnitVM unitViewModel) {
        unitViewModel.params.put("externalId", getExternalId(unit))
    }

    @Override
    void setViewModelContent(ContentHolder unit, UnitVM unitViewModel) {
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
