@Typed package mirari.model

import mirari.ko.UnitViewModel
import mirari.ko.ViewModel
import mirari.model.face.RightsControllable
import mirari.model.strategy.inners.InnersHolder
import mirari.model.strategy.inners.InnersPolicy
import ru.mirari.infra.mongo.Domain
import com.google.code.morphia.annotations.*
import mirari.model.unit.UnitContent
import mirari.model.unit.UnitType

/**
 * @author alari
 * @since 10/27/11 8:19 PM
 */
@Entity("unit")
@Indexes([
@Index("draft"), @Index("owner")
])
abstract class Unit extends Domain implements RightsControllable, InnersHolder {
    transient final InnersPolicy innersPolicy = InnersPolicy.ANY
    transient UnitType unitType

    @Reference Site owner

    String title

    boolean draft = true
    @Indexed
    @Reference Unit outer

    // Where it is originally placed
    @Indexed
    @Reference(lazy = true) Page page

    @Reference(lazy = true) List<Unit> inners

    @Reference(lazy = true) UnitContent content
    Map<String,String> contentData = [:]

    @Override
    void setInners(List<Unit> inners) {
        this.inners = inners
    }

    void setViewModel(UnitViewModel viewModel) {
        title = viewModel.title
    }

    UnitViewModel getViewModel() {
        UnitViewModel uvm = new UnitViewModel(
                id: id.toString(),
                title: title,
                type: type
        )
        innersPolicy.strategy.attachInnersToViewModel(this, uvm)
        uvm
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


    @Override
    void attachInner(Unit u) {
        innersPolicy.strategy.attachInner(this, u)
        u.outer = this
    }

    @Override
    Unit getNextInnerUnit(Unit current) {
        innersPolicy.strategy.getNext(this,current)
    }

    @Override
    Unit getPrevInnerUnit(Unit current) {
        innersPolicy.strategy.getPrev(this,current)
    }

    @Override
    void setInners(ViewModel viewModel) {
        innersPolicy.strategy.setInners(this, viewModel, this.page)
    }

    @Override
    void setInners(ViewModel viewModel, Map<String, Unit> oldInners) {
        innersPolicy.strategy.setInners(this, viewModel, this.page, oldInners)
    }

    @Override
    void deleteInners() {
        innersPolicy.strategy.deleteInners(this)
    }

    @Override
    String getInnersSupportedType() {
        "*"
    }
}
