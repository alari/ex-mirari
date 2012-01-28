@Typed package mirari.model.strategy.content

import eu.medsea.mimeutil.MimeType
import mirari.model.unit.UnitContent

/**
 * @author alari
 * @since 1/6/12 8:13 PM
 */
public interface ContentHolder {
    Map<String, String> getContentData()

    String getStringId()

    UnitContent getContent()

    void setContent(UnitContent content)

    void setContentFile(File file, MimeType type)

    void setContentUrl(String url)

    void deleteContent()
}