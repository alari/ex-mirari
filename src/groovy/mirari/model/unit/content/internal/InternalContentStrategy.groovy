@Typed package mirari.model.unit.content.internal

import mirari.model.unit.content.ContentHolder
import mirari.model.unit.content.ContentStrategy

/**
 * @author alari
 * @since 1/6/12 7:19 PM
 */
abstract class InternalContentStrategy extends ContentStrategy {
    boolean isExternal() {
        false
    }

    @Override
    void buildContentByUrl(ContentHolder unit, String url) {
        void
    }

    @Override
    boolean isUrlSupported(String url) {
        false
    }
}
