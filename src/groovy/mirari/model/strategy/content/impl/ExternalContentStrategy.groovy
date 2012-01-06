package mirari.model.strategy.content.impl

import mirari.model.strategy.content.ContentStrategy
import mirari.model.Unit
import eu.medsea.mimeutil.MimeType

/**
 * @author alari
 * @since 1/6/12 6:47 PM
 */
abstract class ExternalContentStrategy extends ContentStrategy{
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


}
