package mirari.model.strategy.content.external

import mirari.model.strategy.content.ContentStrategy
import mirari.model.Unit
import eu.medsea.mimeutil.MimeType
import mirari.model.strategy.content.ContentData
import mirari.ko.UnitViewModel

/**
 * @author alari
 * @since 1/6/12 6:47 PM
 */
abstract class ExternalContentStrategy extends ContentStrategy{
    @Override
    void attachContentToViewModel(Unit unit, UnitViewModel unitViewModel) {
        unitViewModel.put("params", ["externalId": getExternalId(unit)])
    }

    @Override
    void setViewModelContent(Unit unit, UnitViewModel unitViewModel) {
        if(unitViewModel.params?.externalUrl && isUrlSupported(unitViewModel.params.externalUrl)) {
            buildContentByUrl(unit, unitViewModel.params.externalUrl)
        }
    }
    
    @Override
    boolean isExternal() {
        true
    }

    @Override
    void deleteContent(Unit unit) {
        void
    }

    @Override
    void setContentFile(Unit unit, File file, MimeType type) {
        void
    }

    @Override
    boolean isContentFileSupported(MimeType type) {
        false
    }

    @Override
    void saveContent(Unit unit) {
        void
    }

    void setExternalId(Unit unit, String id) {
        ContentData.EXTERNAL_ID.putTo(unit, id)
    }
    
    String getExternalId(Unit unit) {
        ContentData.EXTERNAL_ID.getFrom(unit)
    }
}
