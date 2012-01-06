@Typed package mirari.dao

import com.google.code.morphia.Key
import mirari.infra.CleanHtmlService
import mirari.repo.UnitContentRepo
import org.springframework.beans.factory.annotation.Autowired
import ru.mirari.infra.mongo.BaseDao
import ru.mirari.infra.mongo.MorphiaDriver
import mirari.model.unit.UnitContent

/**
 * @author alari
 * @since 1/4/12 4:50 PM
 */
class UnitContentDao extends BaseDao<UnitContent> implements UnitContentRepo{
    @Autowired CleanHtmlService cleanHtmlService

    @Autowired
    UnitContentDao(MorphiaDriver morphiaDriver) {
        super(morphiaDriver)
    }

    Key<UnitContent> save(UnitContent content) {
        content.text = cleanHtmlService.clean(content.text)
        super.save(content)
    }
}
