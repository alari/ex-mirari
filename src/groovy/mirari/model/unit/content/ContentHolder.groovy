@Typed package mirari.model.unit.content

import mirari.model.unit.UnitContent
import ru.mirari.infra.file.FileInfo

/**
 * @author alari
 * @since 1/6/12 8:13 PM
 */
public interface ContentHolder {
    Map<String, String> getContentData()

    String getStringId()

    String getTitle()

    void setTitle(String s)

    UnitContent getContent()

    void setContent(UnitContent content)

    void setContentFile(FileInfo fileInfo)

    void setContentUrl(String url)

    void deleteContent()
}