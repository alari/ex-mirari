@Typed package mirari.model.strategy.content

import mirari.ko.UnitViewModel
import mirari.model.Unit
import eu.medsea.mimeutil.MimeType

/**
 * @author alari
 * @since 1/6/12 5:35 PM
 */
abstract class ContentStrategy {
    abstract void attachContentToViewModel(Unit unit, UnitViewModel unitViewModel)
    abstract void setViewModelContent(Unit unit, UnitViewModel unitViewModel)
    abstract void setContentFile(Unit unit, File file, MimeType type)

    abstract boolean isContentFileSupported(MimeType type)

    abstract void saveContent(Unit unit)
    abstract void deleteContent(Unit unit)

    boolean isInternal() {
        !external
    }
    abstract boolean isExternal()

    abstract void buildContentByUrl(Unit unit, String url);

    abstract boolean isUrlSupported(String url);
}
