package mirari.model.strategy.content.external

import mirari.model.Unit

/**
 * @author alari
 * @since 1/6/12 7:33 PM
 */
class RussiaRuContentStrategy extends ExternalContentStrategy{
    @Override
    void buildContentByUrl(Unit unit, String url) {
        if(!isUrlSupported(url)) return;
        URL u = new URL(url)
        //http://russia.ru/video/diskurs_12854/
        setExternalId(unit, u.path.substring(7, u.path.size()-1))
    }

    @Override
    boolean isUrlSupported(String url) {
        URL u = new URL(url)
        u.host in ["russia.ru", "tv.russia.ru", "www.russia.ru"]
    }
}
