@Typed package mirari.model

import mirari.ko.UnitViewModel
import mirari.model.face.RightsControllable
import mirari.model.face.UnitsContainer
import ru.mirari.infra.mongo.Domain
import com.google.code.morphia.annotations.*

/**
 * @author alari
 * @since 10/27/11 8:19 PM
 */
@Entity("unit")
@Indexes([
@Index("draft"), @Index("owner")
])
abstract class Unit extends Domain implements RightsControllable, UnitsContainer {
    @Reference Site owner

    String title

    boolean draft = true
    @Indexed
    @Reference Unit outer

    // Where it is originally placed
    @Indexed
    @Reference(lazy = true) Page page

    @Reference(lazy = true) List<Unit> inners

    void setViewModel(UnitViewModel viewModel) {
        title = viewModel.title
    }

    UnitViewModel getViewModel() {
        UnitViewModel uvm = new UnitViewModel(
                id: id.toString(),
                title: title,
                type: type,
                inners: []
        )
        for (Unit u: inners) {
            uvm.inners.add u.viewModel
        }
        uvm
    }

    void attach(Unit unit) {
        unit.outer = this
        if (inners == null) inners = []
        inners.add unit
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
}
