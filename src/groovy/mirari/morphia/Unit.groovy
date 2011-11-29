@Typed package mirari.morphia

import com.google.code.morphia.query.Query
import org.apache.commons.lang.RandomStringUtils
import org.springframework.beans.factory.annotation.Autowired
import ru.mirari.infra.mongo.BaseDao
import ru.mirari.infra.mongo.Domain
import ru.mirari.infra.mongo.MorphiaDriver
import com.google.code.morphia.annotations.*
import mirari.ko.UnitViewModel
import mirari.UnitProducerService
import com.google.code.morphia.Key
import mirari.morphia.unit.single.TextUnit

/**
 * @author alari
 * @since 10/27/11 8:19 PM
 */
@Entity("unit")
@Indexes([
@Index("draft"), @Index("space")
])
abstract class Unit extends Domain implements RightsControllable{
    @Reference Space space

    String title

    boolean draft = true
    @Indexed
    @Reference Unit outer

    @Reference(lazy = true) List<Unit> inners

    void setViewModel(UnitViewModel viewModel) {
        title = viewModel.title
    }

    UnitViewModel getViewModel(){
        new UnitViewModel(
                id: id.toString(),
                title: title,
                type: type
        )
    }

    void addUnit(Unit unit) {
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
        @Autowired UnitProducerService unitProducerService
        @Autowired TextUnit.Content.Dao textUnitContentDao

        @Autowired
        Dao(MorphiaDriver morphiaDriver) {
            super(morphiaDriver)
        }

        Unit buildFor(UnitViewModel viewModel, Space space) {
            Unit unit
            if(viewModel.id) {
                unit = getById((String)viewModel.id)
            } else {
                unit = unitProducerService.produceUnit(viewModel, space)
            }
            viewModel.assignTo(unit)
            for(UnitViewModel uvm in viewModel.inners) {
                // TODO: it might be an old unit with inners
                // TODO: remove units with _remove
                unit.addUnit buildFor(uvm, space)
                // Todo: external units must be asserted via anchors
            }
            unit
        }

        Key<Unit> save(Unit unit) {
            for(Unit u in unit.inners) {
                save(u)
            }
            if(unit instanceof TextUnit) {
                textUnitContentDao.save(((TextUnit)unit).content)
            }
            super.save(unit)
        }
    }
}
