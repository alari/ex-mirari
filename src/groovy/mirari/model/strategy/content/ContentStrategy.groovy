@Typed package mirari.model.strategy.content

import eu.medsea.mimeutil.MimeType
import mirari.ko.UnitViewModel

/**
 * @author alari
 * @since 1/6/12 5:35 PM
 */
abstract class ContentStrategy {
    abstract void attachContentToViewModel(ContentHolder unit, UnitViewModel unitViewModel)

    abstract void setViewModelContent(ContentHolder unit, UnitViewModel unitViewModel)

    abstract void setContentFile(ContentHolder unit, File file, MimeType type)

    abstract boolean isContentFileSupported(MimeType type)

    abstract void saveContent(ContentHolder unit)

    abstract void deleteContent(ContentHolder unit)

    boolean isInternal() {
        !external
    }

    abstract boolean isExternal()

    abstract void buildContentByUrl(ContentHolder unit, String url);

    abstract boolean isUrlSupported(String url);
}
