@Typed package mirari.model

import com.google.code.morphia.query.Query
import mirari.util.ApplicationContextHolder
import mirari.ko.PageViewModel
import mirari.model.face.NamedThing
import mirari.model.face.RightsControllable
import mirari.model.face.UnitsContainer
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
class Page extends Domain implements NamedThing, RightsControllable, UnitsContainer {
    static protected transient LinkGenerator grailsLinkGenerator

    static {
        grailsLinkGenerator = (LinkGenerator) ApplicationContextHolder.getBean("grailsLinkGenerator")
    }

    String getUrl(Map args = [:]) {
        args.put("for", this)
        grailsLinkGenerator.link(args)
    }

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

    void attach(Unit unit) {
        inners.add(unit)
    }

    PageViewModel getViewModel() {
        PageViewModel uvm = new PageViewModel(
                id: id.toString(),
                title: title,
                type: type,
                draft: draft,
                inners: []
        )
        for (Unit u: inners) {
            uvm.inners.add u.viewModel
        }
        uvm
    }

    void setViewModel(PageViewModel vm) {
        vm.assignTo(this)
    }
}
