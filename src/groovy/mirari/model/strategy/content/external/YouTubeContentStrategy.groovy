@Typed package mirari.model.strategy.content.external

import org.apache.http.client.utils.URLEncodedUtils
import mirari.model.Unit
import mirari.ko.UnitViewModel

/**
 * @author alari
 * @since 1/6/12 7:23 PM
 */
class YouTubeContentStrategy extends ExternalContentStrategy{
    @Override
    void buildContentByUrl(Unit unit, String url) {
        URL u = new URL(url)
        if (u.host == "youtu.be") {
            // http://youtu.be/zi3AqicZgEk
            setExternalId(unit, u.path.substring(1))
        } else if(u.host == "www.youtube.com" && u.path == "/watch") {
            // http://www.youtube.com/watch?v=zi3AqicZgEk&feature=g-logo&context=G2e33cabFOAAAAAAABAA
            setExternalId(unit, URLEncodedUtils.parse(url.toURI(), "UTF-8").find {it.name == "v"}.value)
        }
    }

    @Override
    boolean isUrlSupported(String url) {
        URL u = new URL(url)
        return u.host == "youtu.be" || (u.host == "www.youtube.com" && u.path == "/watch")
    }
}
