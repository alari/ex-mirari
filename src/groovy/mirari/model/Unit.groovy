@Typed package mirari.model

import mirari.model.face.RightsControllable
import mirari.model.strategy.content.ContentHolder
import mirari.model.strategy.content.ContentPolicy
import mirari.model.strategy.inners.InnersHolder
import mirari.model.strategy.inners.InnersHolderDomain
import mirari.model.strategy.inners.InnersPolicy
import mirari.model.unit.UnitContent
import mirari.model.unit.UnitInnersBehaviour
import mirari.util.link.LinkAttributesFitter
import mirari.util.link.LinkUtil
import mirari.vm.UnitVM
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
class Unit extends MorphiaDomain implements RightsControllable, ContentHolder, LinkAttributesFitter, InnersHolderDomain {
    String getUrl(Map args = [:]) {
        args.put("for", this)
        LinkUtil.getUrl(args)
    }

    @Transient
    transient InnersPolicy _innersPolicy = InnersPolicy.ANY

    ContentPolicy getContentPolicy() {
        ContentPolicy.getByName(type)
    }

    void setContentPolicy(ContentPolicy contentPolicy) {
        type = contentPolicy.name
    }

    @Reference Site owner

    String title

    boolean getDraft() {
        page?.draft
    }

    String type

    @Indexed
    @Reference Unit outer

    // Where it is originally placed
    @Indexed
    @Reference(lazy = true) Page page

    @Reference(lazy = true) List<Unit> _inners

    @Reference(lazy = true) UnitContent content
    Map<String, String> contentData = [:]

    void setViewModel(UnitVM viewModel) {
        title = viewModel.title
        contentPolicy.strategy.setViewModelContent(this, viewModel)
    }

    UnitVM getViewModel() {
        UnitVM.build(this)
    }

    boolean isEmpty() {
        if (!contentPolicy.strategy.isEmpty(this)) return false
        if (!inners?.size()) return true
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
        if (title && title.size() > 127) {
            title = title.substring(0, 127)
        }
    }

    String toString() {
        title ?: type
    }

    // *********** inners policy
    @Delegate @Transient
    private InnersHolder innersBehaviour = new UnitInnersBehaviour(this)

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
