@Typed package mirari.dao

import com.google.code.morphia.Key
import com.google.code.morphia.query.Query
import com.mongodb.WriteResult
import mirari.model.Page
import mirari.model.Site
import mirari.model.Tag
import mirari.model.Unit
import mirari.model.disqus.Comment
import mirari.model.page.PageType
import mirari.repo.CommentRepo
import mirari.repo.PageRepo
import mirari.repo.UnitRepo
import org.apache.log4j.Logger
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import ru.mirari.infra.feed.FeedQuery
import ru.mirari.infra.mongo.BaseDao
import ru.mirari.infra.mongo.MorphiaDriver
import mirari.repo.AvatarRepo
import mirari.model.strategy.inners.InnersHolder
import mirari.model.strategy.content.ContentPolicy
import mirari.event.Event
import mirari.event.EventType
import mirari.event.EventMediator

/**
 * @author alari
 * @since 1/4/12 4:38 PM
 */
class PageDao extends BaseDao<Page> implements PageRepo {
    @Autowired private UnitRepo unitRepo
    @Autowired private CommentRepo commentRepo
    @Autowired private AvatarRepo avatarRepo
    static final private Logger log = Logger.getLogger(this)

    @Autowired PageDao(MorphiaDriver morphiaDriver) {
        super(morphiaDriver)
    }

    Page getByName(Site site, String name) {
        createQuery().filter("head.site", site).filter("head.name", name.toLowerCase()).get()
    }

    FeedQuery<Page> feed(Site site) {
        new FeedQuery<Page>(noDraftsQuery.filter("head.sites", site))
    }

    @Override
    FeedQuery<Page> feed(Site site, PageType type) {
        new FeedQuery<Page>(noDraftsQuery.filter("head.sites", site).filter("head.type", type))
    }

    @Override
    FeedQuery<Page> feed(Tag tag) {
        new FeedQuery<Page>(noDraftsQuery.filter("head.tags", tag))
    }

    @Override
    FeedQuery<Page> drafts(Site site) {
        new FeedQuery<Page>(getDraftsQuery(site))
    }

    @Override
    FeedQuery<Page> drafts(Site site, PageType type) {
        new FeedQuery<Page>(getDraftsQuery(site).filter("head.type", type))
    }

    @Override
    FeedQuery<Page> drafts(Tag tag) {
        new FeedQuery<Page>(getDraftsQuery(tag.site).filter("head.tags", tag))
    }

    @Override
    void setPageDraft(Page page, boolean draft) {
        update(createQuery().filter("id", new ObjectId(page.stringId)), createUpdateOperations().set("head.draft", draft))
        EventMediator.instance.fire(EventType.PAGE_DRAFT_CHANGED, [draft:draft, _id: page.stringId])
    }

    WriteResult delete(Page page) {
        for (Comment c: commentRepo.listByPage(page)) {
            commentRepo.delete(c)
        }
        for (Unit u in page.body.inners) {
            unitRepo.delete(u)
        }
        if(!page.head.avatar.basic) {
            avatarRepo.delete(page.head.avatar)
        }
        String pageId = page.stringId
        WriteResult r = super.delete(page)
        EventMediator.instance.fire(EventType.PAGE_DELETED, [_id: pageId])
        r
    }

    Key<Page> save(Page page) {
        // Units has references on page, so we need to save one before
        List<Event> events = []
        if (!page.head.publishedDate && !page.head.draft) {
            page.head.publishedDate = new Date()
            page.firePostPersist(EventType.PAGE_PUBLISHED)
        }
        unitRepo.removeEmptyInners(page.body)
        if (!page.isPersisted()) {
            final List<Unit> inners = page.body.inners
            page.body.inners = []
            super.save(page)
            page.body.inners = inners
        }
        for (Unit u in page.body.inners) {
            unitRepo.save(u)
            System.out.println "Saving ${u} of page"
        }
        super.save(page)
    }

    private Query<Page> getNoDraftsQuery() {
        createQuery().filter("head.draft", false).order("-head.publishedDate")
    }

    private Query<Page> getDraftsQuery(Site owner) {
        createQuery().filter("head.draft", true).filter("head.owner", owner).order("-head.lastUpdated")
    }
}