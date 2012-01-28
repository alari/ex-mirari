@Typed package mirari.dao

import com.google.code.morphia.Key
import com.mongodb.WriteResult
import mirari.ko.UnitViewModel
import mirari.model.Page
import mirari.model.Unit
import mirari.repo.UnitRepo
import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import ru.mirari.infra.mongo.BaseDao
import ru.mirari.infra.mongo.MorphiaDriver

/**
 * @author alari
 * @since 1/4/12 4:52 PM
 */
class UnitDao extends BaseDao<Unit> implements UnitRepo {
    static private final Logger log = Logger.getLogger(this)

    @Autowired UnitDao(MorphiaDriver morphiaDriver) {
        super(morphiaDriver)
    }

    Unit buildFor(UnitViewModel viewModel, Page page) {
        Unit unit
        if (viewModel.id) {
            unit = getById((String) viewModel.id)
        } else {
            unit = new Unit()
            unit.type = viewModel.type
            unit.owner = page.head.owner
            unit.page = page
        }
        unit.viewModel = viewModel
        unit
    }

    Key<Unit> save(Unit unit) {
        List<Unit> setOuters = []
        for (Unit u in unit.inners) {
            if (!unit.isPersisted() && u.outer == unit) {
                u.outer = null
                setOuters.add(u)
            }
            save(u)
        }
        unit.contentPolicy.strategy.saveContent(unit)
        Key<Unit> k = super.save(unit)

        for (Unit u in setOuters) {
            u.outer = unit
            super.save(u)
        }
        k
    }

    WriteResult delete(Unit unit) {
        unit.contentPolicy.strategy.deleteContent(unit)
        unit.deleteContent()
        unit.deleteInners()
        super.delete(unit)
    }
}