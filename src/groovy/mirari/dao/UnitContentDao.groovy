@Typed package mirari.dao

import com.google.code.morphia.Key
import mirari.model.unit.UnitContent
import mirari.repo.UnitContentRepo
import org.springframework.beans.factory.annotation.Autowired
import ru.mirari.infra.mongo.BaseDao
import ru.mirari.infra.mongo.MorphiaDriver

/**
 * @author alari
 * @since 1/4/12 4:50 PM
 */
class UnitContentDao extends BaseDao<UnitContent> implements UnitContentRepo {
    @Autowired
    UnitContentDao(MorphiaDriver morphiaDriver) {
        super(morphiaDriver)
    }

    Key<UnitContent> save(UnitContent uc) {
        if (!uc.persisted || uc.modified) {
            return super.save(uc)
        }
        null
    }
}
