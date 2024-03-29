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
import mirari.model.image.thumb.ThumbOrigin
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
import mirari.repo.PageFeedRepo

import mirari.repo.TagRepo
import mirari.model.image.CommonImage
import mirari.model.image.PageImage
import mirari.repo.NoticeRepo

/**
 * @author alari
 * @since 1/4/12 4:38 PM
 */
class PageDao extends BaseDao<Page> implements PageRepo {
    @Autowired private UnitRepo unitRepo
    @Autowired private CommentRepo commentRepo
    @Autowired private AvatarRepo avatarRepo
    @Autowired PageFeedRepo pageFeedRepo
    @Autowired TagRepo tagRepo
    @Autowired NoticeRepo noticeRepo
    static final private Logger log = Logger.getLogger(this)

    @Autowired
    PageDao(MorphiaDriver morphiaDriver) {
        super(morphiaDriver)
    }

    @Override
    Page getByName(final Site site, final String name) {
        createQuery().filter("site", site).filter("nameSorting", name.toLowerCase()).get()
    }

    @Override
    FeedQuery<Page> feed(final Site site) {
        new FeedQuery<Page>(noDraftsQuery.filter("placedOnSites", site).filter("type !=", PageType.PAGE))
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
        new FeedQuery<Page>(getDraftsQuery(site).filter("type !=", PageType.PAGE))
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
    void setImage(final Page page, final CommonImage image, int thumbOrigin) {
        update(createQuery().filter("id", new ObjectId(page.stringId)), createUpdateOperations().set("image", new PageImage(image, thumbOrigin)))
    }

    @Override
    void setImage(final Site owner, final CommonImage image) {
        update(
                createQuery().filter("owner", owner).filter("image.origin <=", ThumbOrigin.OWNER_AVATAR),
                createUpdateOperations().set("image", new PageImage(image, ThumbOrigin.OWNER_AVATAR))
        )
    }

    @Override
    void setImage(final Site owner) {
        for (Page p in createQuery().filter("owner", owner).filter("image.origin", ThumbOrigin.OWNER_AVATAR).fetch()) {
            setImage(p, (CommonImage)avatarRepo.getBasic(p.type.name), ThumbOrigin.TYPE_DEFAULT)
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
        Map deletedParams = [_id: page.stringId, type: page.type.name, sites: page.placedOnSites*.stringId, draft: page.isDraft()]

        noticeRepo.removeByPage(page)
        WriteResult r = super.delete(page)

        EventMediator.instance.fire(EventType.PAGE_DELETED, deletedParams)
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
        if (!page.publishedDate && !page.draft) {
            page.publishedDate = new Date()
            page.firePostPersist(EventType.PAGE_PUBLISHED, [fromDrafts: page.isPersisted()])
        }
        if (!page.isPersisted()) {
            final List<Unit> inners = page.inners
            page.inners = []
            super.save(page)
            page.inners = inners
        }
        for (Unit u in page.inners) {
            unitRepo.save(u)
        }
        if(page.type == PageType.PAGE) {
            pageFeedRepo.updateByPage(page)
            tagRepo.updateByPage(page)
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