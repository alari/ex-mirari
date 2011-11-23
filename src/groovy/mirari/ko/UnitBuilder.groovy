@Typed package mirari.ko

import mirari.morphia.Space
import mirari.morphia.Unit
import mirari.morphia.space.subject.Person
import mirari.morphia.unit.coll.ImageCollUnit

import org.apache.log4j.Logger
import mirari.ServiceResponse
import mirari.UnitProducerService

/**
 * @author alari
 * @since 11/20/11 8:55 PM
 */
class UnitBuilder {
    static private final Logger log = Logger.getLogger(this)

    final Space space
    final Person person

    UnitProducerService unitProducerService

    public ServiceResponse resp = new ServiceResponse()
    public Unit unit

    private Unit.Dao getUnitDao() {
        unitProducerService.unitDao
    }

    UnitBuilder(final Space space, final Person person, UnitProducerService unitProducerService) {
        this.space = space
        this.person = person
        this.unitProducerService = unitProducerService
    }

    @Typed(TypePolicy.DYNAMIC)
    private List<String> getTypes(final UnitViewModel vm) {
        vm.units.collect {it.type}.unique()
    }

    private void buildForManyContents(final UnitViewModel vm, boolean draft) {
        List<String> types = getTypes(vm)
        if (types.size() != 1) {
            resp.error("unitBuilder.error.types")
            log.error("Too much types: "+types)
            return
        }

        String type = types.first()
        if (!type.equalsIgnoreCase("image")) {
            resp.error("unitBuilder.error.typeNotImplemented", [type])
            return
        }

        unit = new ImageCollUnit(title: vm.title, space: space)
        unitDao.save(unit)
        for (UnitViewModel uvm in vm.units) {
            Unit u = unitDao.getById(uvm.id)
            u.viewModel = uvm
            u.container = unit
            u.draft = draft
            unit.addUnit(u)
            unitDao.save(u)
        }
        unit.draft = draft
        unitDao.save(unit)
        resp.success("unitBuilder.success.coll")
    }

    private void buildForSingleContent(final UnitViewModel vm, boolean draft) {
        unit = unitProducerService.produceUnit(vm.units.first(), space, person)

        if (!unit || unit.id == null) {
            resp.error("unit not found for id: ${vm.id}")
            return
        }

        unit.draft = draft
        unit.viewModel = vm.units.first()

        unitDao.save(unit)

        if (unit.id) {
            resp.success("unitBuilder.add.success")
        } else {
            resp.error "unitBuilder.add.error.cannotSave"
            resp.model vm
        }
    }

    public UnitBuilder buildFor(final UnitViewModel vm, boolean draft) {
        if (vm.units.size() > 1) {
            buildForManyContents(vm, draft)
        } else {
            buildForSingleContent(vm, draft)
        }
        this
    }
}
