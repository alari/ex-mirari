@Typed package mirari.dao

import ru.mirari.infra.mongo.BaseDao
import mirari.repo.TextUnitContentRepo
import org.springframework.beans.factory.annotation.Autowired
import mirari.infra.CleanHtmlService
import ru.mirari.infra.mongo.MorphiaDriver
import com.google.code.morphia.Key
import mirari.model.unit.single.TextUnit

/**
 * @author alari
 * @since 1/4/12 4:50 PM
 */
class TextUnitContentDao extends BaseDao<TextUnit.Content> implements TextUnitContentRepo{
    @Autowired CleanHtmlService cleanHtmlService

    @Autowired TextUnitContentDao(MorphiaDriver morphiaDriver) {
        super(morphiaDriver)
    }

    Key<TextUnit.Content> save(TextUnit.Content content) {
        content.text = cleanHtmlService.clean(content.text)
        super.save(content)
    }
}
