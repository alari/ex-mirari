@Typed package mirari.model

import mirari.ko.PageViewModel
import mirari.ko.ViewModel
import mirari.model.face.NamedThing
import mirari.model.face.RightsControllable
import mirari.model.strategy.inners.InnersHolder
import mirari.model.strategy.inners.InnersPolicy
import mirari.util.ApplicationContextHolder
import org.apache.commons.lang.RandomStringUtils
import org.codehaus.groovy.grails.web.mapping.LinkGenerator
import ru.mirari.infra.mongo.Domain
import com.google.code.morphia.annotations.*

/**
 * @author alari
 * @since 11/28/11 1:29 PM
 */
@Entity("page")
@Indexes([
@Index("site"), @Index("-lastUpdated"), @Index("draft"),
@Index(value = "site,name", unique = true, dropDups = true)
])
class Page extends Domain implements NamedThing, RightsControllable, InnersHolder {
    static protected transient LinkGenerator grailsLinkGenerator

    static {
        grailsLinkGenerator = (LinkGenerator) ApplicationContextHolder.getBean("grailsLinkGenerator")
    }

    String getUrl(Map args = [:]) {
        args.put("for", this)
        grailsLinkGenerator.link(args)
    }

    transient final InnersPolicy innersPolicy = InnersPolicy.ALL

    // where (site)
    @Reference Site site
    // who
    @Reference Site owner
    // what
    @Reference(lazy = true) List<Unit> inners = []
    // named after
    String name = RandomStringUtils.randomAlphanumeric(5).toLowerCase()
    String title
    // permissions
    boolean draft = true
    // kind of
    String type = "page"
    // when
    Date dateCreated = new Date();
    Date lastUpdated = new Date();

    @PrePersist
    void prePersist() {
        lastUpdated = new Date();
    }

    String toString() {
        title ?: type
    }

    PageViewModel getViewModel() {
        PageViewModel uvm = new PageViewModel(
                id: id.toString(),
                title: title,
                type: type,
                draft: draft
        )
        innersPolicy.strategy.attachInnersToViewModel(this, uvm)
        uvm
    }

    void setViewModel(PageViewModel vm) {
        vm.assignTo(this)
    }

    @Override
    void setInners(List<Unit> inners) {
        this.inners = inners
    }


    @Override
    void attachInner(Unit u) {
        innersPolicy.strategy.attachInner(this, u)
    }

    @Override
    Unit getNextInnerUnit(Unit current) {
        innersPolicy.strategy.getNext(this, current)
    }

    @Override
    Unit getPrevInnerUnit(Unit current) {
        innersPolicy.strategy.getPrev(this, current)
    }

    @Override
    void setInners(ViewModel viewModel) {
        innersPolicy.strategy.setInners(this, viewModel)
    }

    @Override
    void setInners(ViewModel viewModel, Map<String, Unit> oldInners) {
        innersPolicy.strategy.setInners(this, viewModel, this, oldInners)
    }

    @Override
    void deleteInners() {
        innersPolicy.strategy.deleteInners(this)
    }
}
