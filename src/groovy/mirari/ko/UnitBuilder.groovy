@Typed package mirari.ko

import mirari.morphia.Space
import mirari.morphia.Unit
import mirari.morphia.space.subject.Person
import mirari.morphia.unit.collection.ImageCollectionUnit

import org.apache.log4j.Logger
import mirari.ServiceResponse

/**
 * @author alari
 * @since 11/20/11 8:55 PM
 */
class UnitBuilder {
    static private final Logger log = Logger.getLogger(this)

    final Space space
    final Person person

    // TODO: inject beans
    final Unit.Dao unitDao

    public ServiceResponse resp = new ServiceResponse()
    public Unit unit

    UnitBuilder(final Space space, final Person person, Unit.Dao unitDao) {
        this.space = space
        this.person = person
        this.unitDao = unitDao
    }

    private void buildForManyContents(final UnitViewModel vm, boolean draft) {
        List<String> types = vm.contents.collect {it.type}.unique()
        if (types.size() != 1) {
            resp.error("too much types, not implemented yet: " + types)
            return
        }

        String type = types.first()
        if (!type.equalsIgnoreCase("image")) {
            resp.error("cannot work with non-image units (${type})")
            return
        }

        unit = new ImageCollectionUnit(title: vm.title, space: space)
        unitDao.save(unit)
        for (UnitViewModel uvm in vm.contents) {
            Unit u = unitDao.getById(uvm.id)
            u.title = uvm.title
            u.container = unit
            u.draft = draft
            unit.addUnit(u)
            unitDao.save(u)
        }
        unit.draft = draft
        unitDao.save(unit)
        resp.success("container saved")
    }

    private void buildForSingleContent(final UnitViewModel vm, boolean draft) {
        unit = unitDao.getById(vm.id)
        if (!unit || unit.id == null) {
            resp.error("unit not found for id: ${vm.id}")
            return
        }

        unit.draft = draft
        unit.title = vm.title
        unitDao.save(unit)

        if (unit.id) {
            resp.success("unitBuilder.add.success")
        } else {
            resp.error "unitBuilder.add.error.cannotSave"
            resp.model vm.toMap()
        }
    }

    public UnitBuilder buildFor(final UnitViewModel vm, boolean draft) {
        if (vm.contents.size() > 1) {
            buildForManyContents(vm, draft)
        } else {
            buildForSingleContent(vm, draft)
        }
        this
    }
}
