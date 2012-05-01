@Typed package mirari.model.unit

import ru.mirari.infra.mongo.MorphiaDomain
import com.google.code.morphia.annotations.Entity
import com.google.code.morphia.annotations.Transient

/**
 * @author alari
 * @since 1/6/12 4:10 PM
 */
@Entity("page.unit.content")
class UnitContent extends MorphiaDomain {
    String text

    void setText(String s) {
        if (text != s) {
            text = s
            modified = true
        }
    }

    @Transient
    transient private boolean modified = false

    boolean isModified() {
        modified
    }
}
