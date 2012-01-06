@Typed package mirari.dao

import com.google.code.morphia.Key
import com.mongodb.WriteResult
import mirari.UnitProducerService
import mirari.ko.UnitViewModel
import mirari.model.Page
import mirari.model.Unit
import mirari.model.unit.single.ImageUnit
import mirari.model.unit.single.TextUnit

import mirari.repo.UnitRepo
import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import ru.mirari.infra.FileStorageService
import ru.mirari.infra.file.FileHolder
import ru.mirari.infra.image.ImageHolder
import ru.mirari.infra.image.ImageStorageService
import ru.mirari.infra.mongo.BaseDao
import ru.mirari.infra.mongo.MorphiaDriver
import mirari.repo.UnitContentRepo

/**
 * @author alari
 * @since 1/4/12 4:52 PM
 */
class UnitDao extends BaseDao<Unit> implements UnitRepo{
    static private final Logger log = Logger.getLogger(this)
    @Autowired UnitProducerService unitProducerService
    @Autowired UnitContentRepo unitContentRepo
    @Autowired ImageStorageService imageStorageService
    @Autowired FileStorageService fileStorageService

    @Autowired
    UnitDao(MorphiaDriver morphiaDriver) {
        super(morphiaDriver)
    }

    Unit buildFor(UnitViewModel viewModel, Page page) {
        Unit unit
        if(viewModel.id) {
            unit = getById((String)viewModel.id)
        } else {
            unit = getUnitForType(viewModel.type)
            unit.owner = page.owner
            // unit.page = page
        }
        viewModel.assignTo(unit)
        unit
    }

    Unit getUnitForType(String type) {
        switch(type.toLowerCase()) {
            case "image": return new ImageUnit()
            case "text": return new TextUnit()
        }
    }

    Key<Unit> save(Unit unit) {
        List<Unit> setOuters = []
        for(Unit u in unit.inners) {
            if(!unit.id && u.outer == unit) {
                u.outer = null
                setOuters.add(u)
            }
            save(u)
        }
        if(unit.content) {
            unitContentRepo.save(unit.content)
        }
        Key<Unit> k = super.save(unit)

        for(Unit u in setOuters) {
            u.outer = unit
            super.save(u)
        }
        k
    }

    WriteResult delete(Unit unit) {
        if(unit instanceof FileHolder) {
            fileStorageService.delete((FileHolder)unit)
        }
        if(unit instanceof ImageHolder) {
            imageStorageService.delete((ImageHolder)unit)
        }
        unit.deleteInners()
        super.delete(unit)
    }
}