@Typed package mirari.dao

import com.google.code.morphia.Key
import com.google.code.morphia.query.Query
import com.mongodb.WriteResult
import mirari.event.EventMediator
import mirari.event.EventType
import mirari.model.Page
import mirari.model.Site
import mirari.model.Tag
import mirari.model.Unit
import mirari.model.disqus.Comment
import mirari.model.page.PageType
import mirari.model.page.thumb.ThumbOrigin
import mirari.repo.AvatarRepo
import mirari.repo.CommentRepo
import mirari.repo.PageRepo
import mirari.repo.UnitRepo
import org.apache.commons.lang.RandomStringUtils
import org.apache.log4j.Logger
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import ru.mirari.infra.feed.FeedQuery
import ru.mirari.infra.mongo.BaseDao
import ru.mirari.infra.mongo.MorphiaDriver

/**
 * @author alari
 * @since 1/4/12 4:38 PM
 */
class PageDao extends BaseDao<Page> implements PageRepo {
    @Autowired private UnitRepo unitRepo
    @Autowired private CommentRepo commentRepo
    @Autowired private AvatarRepo avatarRepo
    static final private Logger log = Logger.getLogger(this)

    @Autowired
    PageDao(MorphiaDriver morphiaDriver) {
        super(morphiaDriver)
    }

    Page getByName(final Site site, final String name) {
        createQuery().filter("site", site).filter("nameSorting", name.toLowerCase()).get()
    }

    FeedQuery<Page> feed(final Site site) {
        new FeedQuery<Page>(noDraftsQuery.filter("placedOnSites", site))
    }

    @Override
    FeedQuery<Page> feed(final Site site, final PageType type) {
        new FeedQuery<Page>(noDraftsQuery.filter("placedOnSites", site).filter("type", type))
    }

    @Override
    FeedQuery<Page> feed(final Tag tag) {
        new FeedQuery<Page>(noDraftsQuery.filter("_tags", tag))
    }

    @Override
    FeedQuery<Page> drafts(final Site site) {
        new FeedQuery<Page>(getDraftsQuery(site))
    }

    @Override
    FeedQuery<Page> drafts(final Site site, final PageType type) {
        new FeedQuery<Page>(getDraftsQuery(site).filter("type", type))
    }

    @Override
    FeedQuery<Page> drafts(final Tag tag) {
        new FeedQuery<Page>(getDraftsQuery(tag.site).filter("_tags", tag))
    }

    /*      Modifiers       */

    @Override
    void setPageDraft(final Page page, boolean draft) {
        update(createQuery().filter("id", new ObjectId(page.stringId)), createUpdateOperations().set("draft", draft))
        EventMediator.instance.fire(EventType.PAGE_DRAFT_CHANGED, [draft: draft, _id: page.stringId])
    }

    @Override
    void setThumbSrc(final Page page, String thumbSrc, int thumbOrigin) {
        update(createQuery().filter("id", new ObjectId(page.stringId)), createUpdateOperations().set("thumbSrc", thumbSrc).set("thumbOrigin", thumbOrigin))
    }

    @Override
    void setThumbSrc(final Site owner, String thumbSrc) {
        update(
                createQuery().filter("owner", owner).filter("thumbOrigin <=", ThumbOrigin.OWNER_AVATAR),
                createUpdateOperations().set("thumbSrc", thumbSrc).set("thumbOrigin", ThumbOrigin.OWNER_AVATAR)
        )
    }

    @Override
    void setThumbSrc(final Site owner) {
        for (Page p in createQuery().filter("owner", owner).filter("thumbOrigin", ThumbOrigin.OWNER_AVATAR).fetch()) {
            setThumbSrc(p, avatarRepo.getBasic(p.type.name).srcThumb, ThumbOrigin.TYPE_DEFAULT)
        }
    }

    WriteResult delete(Page page) {
        for (Comment c: commentRepo.listByPage(page)) {
            commentRepo.delete(c)
        }
        for (Unit u in page.inners) {
            unitRepo.delete(u)
        }
        if (!page.avatar.basic) {
            avatarRepo.delete(page.avatar)
        }
        String pageId = page.stringId
        WriteResult r = super.delete(page)
        EventMediator.instance.fire(EventType.PAGE_DELETED, [_id: pageId])
        r
    }

    Key<Page> save(Page page) {
        // Units has references on page, so we need to save one before
        unitRepo.removeEmptyInners(page)
        if(isPageNameLocked(page)) {
            page.name += "-"
            page.name += RandomStringUtils.randomAlphanumeric(1).toLowerCase()
            while (isPageNameLocked(page)) {
                page.name += RandomStringUtils.randomAlphanumeric(1).toLowerCase()
            }
        }
        if (!page.isPersisted()) {
            final List<Unit> inners = page.inners
            page.inners = []
            super.save(page)
            page.inners = inners
        }
        if (!page.publishedDate && !page.draft) {
            page.publishedDate = new Date()
            page.firePostPersist(EventType.PAGE_PUBLISHED)
        }
        for (Unit u in page.inners) {
            unitRepo.save(u)
        }
        super.save(page)
    }

    private boolean isPageNameLocked(final Page page) {
        final Page current = createQuery().filter("site", page.site).filter("nameSorting", page.name.toLowerCase()).get()
        current && current != page
    }

    private Query<Page> getNoDraftsQuery() {
        createQuery().filter("draft", false).order("-publishedDate")
    }

    private Query<Page> getDraftsQuery(final Site owner) {
        createQuery().filter("draft", true).filter("owner", owner).order("-lastUpdated")
    }
}