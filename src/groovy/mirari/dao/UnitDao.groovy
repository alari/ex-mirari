@Typed package mirari.dao

import ru.mirari.infra.mongo.BaseDao
import mirari.repo.UnitRepo
import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import mirari.UnitProducerService
import mirari.repo.TextUnitContentRepo
import ru.mirari.infra.image.ImageStorageService
import ru.mirari.infra.FileStorageService
import ru.mirari.infra.mongo.MorphiaDriver
import mirari.ko.UnitViewModel
import mirari.model.Page
import mirari.model.face.UnitsContainer
import mirari.model.unit.single.ImageUnit
import mirari.model.unit.single.TextUnit
import com.google.code.morphia.Key
import com.mongodb.WriteResult
import ru.mirari.infra.file.FileHolder
import ru.mirari.infra.image.ImageHolder
import mirari.model.Unit

/**
 * @author alari
 * @since 1/4/12 4:52 PM
 */
class UnitDao extends BaseDao<Unit> implements UnitRepo{
    static private final Logger log = Logger.getLogger(this)
    @Autowired UnitProducerService unitProducerService
    @Autowired TextUnitContentRepo textUnitContentRepo
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

    void attachUnits(UnitsContainer outer, List<UnitViewModel> _inners, Page page, Map<String, Unit> oldUnits = null) {
        if(oldUnits == null) {
            oldUnits = collectUnits(outer)
        }

        outer.inners = []
        for(UnitViewModel uvm in _inners) {
            Unit u
            if(uvm.id && oldUnits.containsKey(uvm.id)) {
                if(uvm._destroy) {
                    continue
                }
                u = oldUnits.remove(uvm.id)
            } else {
                u = buildFor(uvm, page)
            }
            uvm.assignTo(u)
            // Todo: external units must be asserted via anchors
            outer.attach u
            attachUnits(u, uvm.inners, page, oldUnits)
        }
    }

    Map<String, Unit> collectUnits(UnitsContainer outer) {
        Map<String, Unit> units = [:]
        if(outer.inners.size()) for(Unit u : outer.inners) {
            units.put(u.id.toString(), u)
            units.putAll(collectUnits(u))
        }
        units
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
        if(unit instanceof TextUnit) {
            textUnitContentRepo.save(((TextUnit)unit).content)
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
        // TODO: delete inners
        super.delete(unit)
    }
}