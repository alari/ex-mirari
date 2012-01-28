@Typed package mirari.model.unit

import ru.mirari.infra.mongo.MorphiaDomain
import groovy.beans.Bindable

/**
 * @author alari
 * @since 1/6/12 4:10 PM
 */
class UnitContent extends MorphiaDomain{
    String text

    void setText(String s) {
        if(text != s) {
            text = s
            modified = true
        }
    }

    transient private boolean modified = false

    boolean isModified() {
        modified
    }
}
