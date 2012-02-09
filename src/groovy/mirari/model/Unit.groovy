@Typed package mirari.model

import mirari.ko.InnersHolderViewModel
import mirari.ko.SiteInfoViewModel
import mirari.ko.UnitViewModel
import mirari.model.face.RightsControllable
import mirari.model.strategy.content.ContentHolder
import mirari.model.strategy.content.ContentPolicy
import mirari.model.strategy.inners.InnersHolder
import mirari.model.strategy.inners.InnersPolicy
import mirari.model.unit.UnitContent
import mirari.util.link.LinkAttributesFitter
import mirari.util.link.LinkUtil
import org.apache.commons.httpclient.util.DateUtil
import ru.mirari.infra.file.FileInfo
import ru.mirari.infra.mongo.MorphiaDomain
import com.google.code.morphia.annotations.*

/**
 * @author alari
 * @since 10/27/11 8:19 PM
 */
@Entity("unit")
@Indexes([
@Index("draft"), @Index("owner")
])
class Unit extends MorphiaDomain implements RightsControllable, InnersHolder, ContentHolder, LinkAttributesFitter {
    String getUrl(Map args = [:]) {
        args.put("for", this)
        LinkUtil.getUrl(args)
    }

    transient InnersPolicy innersPolicy = InnersPolicy.ANY

    ContentPolicy getContentPolicy() {
        ContentPolicy.getByName(type)
    }

    void setContentPolicy(ContentPolicy contentPolicy) {
        type = contentPolicy.name
    }

    @Reference Site owner

    String title

    boolean isDraft() {
        page?.draft
    }

    String type

    @Indexed
    @Reference Unit outer

    // Where it is originally placed
    @Indexed
    @Reference(lazy = true) Page page

    @Reference(lazy = true) List<Unit> inners

    @Reference(lazy = true) UnitContent content
    Map<String, String> contentData = [:]

    @Override
    void setInners(List<Unit> inners) {
        this.inners = inners
    }

    void setViewModel(UnitViewModel viewModel) {
        title = viewModel.title
        contentPolicy.strategy.setViewModelContent(this, viewModel)
    }

    UnitViewModel getViewModel() {
        UnitViewModel uvm = new UnitViewModel(
                id: stringId,
                title: title,
                type: type,
                dateCreated: DateUtil.formatDate(dateCreated),
                lastUpdated: DateUtil.formatDate(lastUpdated),
                url: getUrl(),
                owner: SiteInfoViewModel.buildFor(owner),
                outerId: outer?.stringId
        )
        innersPolicy.strategy.attachInnersToViewModel(this, uvm)
        contentPolicy.strategy.attachContentToViewModel(this, uvm)
        uvm
    }

    boolean isEmpty() {
        if (!contentPolicy.strategy.isEmpty(this)) return false
        if (!inners.size()) return true
        for (Unit u: inners) {
            if (!u.isEmpty()) {
                return false
            }
        }
        true
    }

    @Version
    long version;

    Date dateCreated = new Date();
    Date lastUpdated = new Date();

    @PrePersist
    void prePersist() {
        lastUpdated = new Date();
    }

    String toString() {
        title ?: type
    }

    // *********** inners policy

    @Override
    void attachInner(Unit u) {
        innersPolicy.strategy.attachInner(this, u)
        u.outer = this
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
    void setInners(InnersHolderViewModel viewModel) {
        innersPolicy.strategy.setInners(this, viewModel, this.page)
    }

    @Override
    void setInners(InnersHolderViewModel viewModel, Map<String, Unit> oldInners) {
        innersPolicy.strategy.setInners(this, viewModel, this.page, oldInners)
    }

    @Override
    void deleteInners() {
        innersPolicy.strategy.deleteInners(this)
    }

    // ************ content policy
    @Override
    void setContentFile(FileInfo fileInfo) {
        contentPolicy.strategy.setContentFile(this, fileInfo)
    }

    @Override
    void setContentUrl(String url) {
        contentPolicy.strategy.buildContentByUrl(this, url)
    }

    @Override
    void deleteContent() {
        contentPolicy.strategy.deleteContent(this)
    }

    @Override
    @Typed
    void fitLinkAttributes(Map attributes) {
        attributes.controller = attributes.controller ?: "siteUnit"
        attributes.base = "http://".concat(owner.host)
        ((Map) attributes.params).id = stringId
    }


}
