@Typed package mirari.morphia

import org.springframework.beans.factory.annotation.Autowired
import ru.mirari.infra.mongo.BaseDao
import ru.mirari.infra.mongo.Domain
import ru.mirari.infra.mongo.MorphiaDriver
import com.google.code.morphia.annotations.*
import mirari.ko.UnitViewModel
import mirari.UnitProducerService
import com.google.code.morphia.Key
import mirari.morphia.unit.single.TextUnit
import mirari.morphia.unit.single.ImageUnit
import com.mongodb.WriteResult
import ru.mirari.infra.image.ImageStorageService
import ru.mirari.infra.FileStorageService
import ru.mirari.infra.file.FileHolder
import ru.mirari.infra.image.ImageHolder
import org.apache.log4j.Logger

/**
 * @author alari
 * @since 10/27/11 8:19 PM
 */
@Entity("unit")
@Indexes([
@Index("draft"), @Index("owner")
])
abstract class Unit extends Domain implements RightsControllable, UnitsContainer{
    @Reference Site owner

    String title

    boolean draft = true
    @Indexed
    @Reference Unit outer

    // Where it is originally placed
    @Indexed
    @Reference(lazy=true) Page page

    @Reference(lazy = true) List<Unit> inners

    void setViewModel(UnitViewModel viewModel) {
        title = viewModel.title
    }

    UnitViewModel getViewModel(){
        UnitViewModel uvm = new UnitViewModel(
                id: id.toString(),
                title: title,
                type: type,
                inners: []
        )
        for(Unit u : inners) {
            uvm.inners.add u.viewModel
        }
        uvm
    }

    void attach(Unit unit) {
        if (unit.outer == null || unit.outer == this) {
            unit.outer = this
            if (inners == null) inners = []
            inners.add unit
        } else {
            throw new IllegalArgumentException("You should build and use anchorUnit")
        }
    }

    transient final public String type = this.getClass().simpleName.substring(0, this.getClass().simpleName.size() - 4)

    // @Version
    Long version;

    Date dateCreated = new Date();
    Date lastUpdated = new Date();

    @PrePersist
    void prePersist() {
        lastUpdated = new Date();
    }

    String toString() {
        title ?: type
    }

    static public class Dao extends BaseDao<Unit> {
        static private final Logger log = Logger.getLogger(this)
        @Autowired UnitProducerService unitProducerService
        @Autowired TextUnit.Content.Dao textUnitContentDao
        @Autowired ImageStorageService imageStorageService
        @Autowired FileStorageService fileStorageService

        @Autowired
        Dao(MorphiaDriver morphiaDriver) {
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
            
            attachUnits(unit, viewModel.inners, page)
            unit
        }
        
        void attachUnits(UnitsContainer outer, List<UnitViewModel> _inners, Page page) {
            Map<String,Unit> inners = [:]
            for(Unit u : outer.inners) {
                inners.put(u.id.toString(), u)
            }
            outer.inners = []
            for(UnitViewModel uvm in _inners) {
                Unit u
                if(uvm.id && inners.containsKey(uvm.id)) {
                    u = inners.remove(uvm.id)
                    if(uvm._destroy) {
                        continue
                    }
                } else {
                    u = buildFor(uvm, page)
                }
                outer.attach u
                // Todo: external units must be asserted via anchors
            }
            // We have units not presented super; somehow we should mark them to delete?
            if(inners.size() > 0) {
                for(Unit u : inners.values()) {
                    log.error "Deleting ${u} from inners"
                }
            }
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
                textUnitContentDao.save(((TextUnit)unit).content)
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
}
