@Typed package mirari.model.strategy.content

import mirari.ko.UnitViewModel
import ru.mirari.infra.file.FileInfo

/**
 * @author alari
 * @since 1/6/12 5:35 PM
 */
abstract class ContentStrategy {
    abstract void attachContentToViewModel(ContentHolder unit, UnitViewModel unitViewModel)

    abstract void setViewModelContent(ContentHolder unit, UnitViewModel unitViewModel)

    abstract void setContentFile(ContentHolder unit, FileInfo fileInfo)

    abstract boolean isContentFileSupported(FileInfo info)

    abstract void saveContent(ContentHolder unit)

    abstract void deleteContent(ContentHolder unit)

    boolean isInternal() {
        !external
    }

    abstract boolean isExternal()

    abstract void buildContentByUrl(ContentHolder unit, String url);

    abstract boolean isUrlSupported(String url);
}
