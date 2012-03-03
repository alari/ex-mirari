package mirari.model.unit.content.internal

import mirari.model.unit.content.ContentHolder
import mirari.vm.UnitVM
import ru.mirari.infra.file.FileInfo
import mirari.model.unit.content.ContentData
import ru.mirari.infra.feed.FeedQuery
import mirari.model.Page
import mirari.model.Site
import org.springframework.beans.factory.annotation.Autowired
import mirari.repo.SiteRepo
import mirari.model.Tag
import mirari.model.page.PageType
import mirari.repo.PageRepo
import mirari.repo.TagRepo
import mirari.util.I18n
import mirari.model.Unit

/**
 * @author alari
 * @since 2/22/12 4:35 PM
 */
class FeedContentStrategy extends InternalContentStrategy{

    @Autowired SiteRepo siteRepo
    @Autowired PageRepo pageRepo
    @Autowired TagRepo tagRepo
    @Autowired I18n i18n

    static final String STYLE_LINKS = "links"
    static final String STYLE_BLOG = "blog"
    static final String STYLE_FULL = "full"
    static final String STYLE_WIDE = "wide"
    static final String STYLE_SMALL = "small"
    static final String STYLE_NONE = "none"

    @Override
    void attachContentToViewModel(ContentHolder unit, UnitVM unitViewModel) {
        unitViewModel.params = [
                num: ContentData.FEED_NUM.getFrom(unit),
                style: ContentData.FEED_STYLE.getFrom(unit),
                source: ContentData.FEED_SOURCE.getFrom(unit),
                locked: ContentData.FEED_LOCKED.getFrom(unit),
                feedId: ContentData.FEED_ID.getFrom(unit),
                last: ContentData.FEED_LAST.getFrom(unit),
        ]
    }

    @Override
    void setViewModelContent(ContentHolder unit, UnitVM unitViewModel) {
        ContentData.FEED_NUM.putTo(unit, unitViewModel.params.num ?: "10")
        ContentData.FEED_STYLE.putTo(unit, unitViewModel.params.style ?: STYLE_LINKS)
        ContentData.FEED_LAST.putTo(unit, unitViewModel.params.last ?: STYLE_NONE)

        if(!ContentData.FEED_LOCKED.getFrom(unit)) {
            ContentData.FEED_SOURCE.putTo(unit, unitViewModel.params.source ?: "all")
            ContentData.FEED_LOCKED.putTo(unit, unitViewModel.params.locked ?: "")
            ContentData.FEED_ID.putTo(unit, unitViewModel.params.feedId ?: "")
            if(unitViewModel.params.source == "tag" && !ContentData.FEED_ID.getFrom(unit)) {
                ContentData.FEED_ID.putTo(unit, tagRepo.getByDisplayNameAndSite(unitViewModel.title, unit.owner)?.stringId)
                ContentData.FEED_LOCKED.putTo(unit, "1")
            }
        }
    }

    FeedQuery<Page> feed(Unit unit) {
        if(unit.type != "feed") return;
        feedHelper(ContentData.FEED_SOURCE.getFrom(unit), ContentData.FEED_ID.getFrom(unit), unit.owner)
    }

    FeedQuery<Page> feed(UnitVM u) {
        if(u.type != "feed") {
            return
        }

        Site owner = siteRepo.getById u.owner.id
        feedHelper(u.params.source, u.params.feedId, owner, u)
    }

    FeedQuery<Page> drafts(UnitVM u) {
        if(u.type != "feed") {
            return
        }

        Site owner = siteRepo.getById u.owner.id
        FeedQuery<Page> drafts

        if (u.params.source == "all") {
                drafts = pageRepo.drafts(owner)
        } else if (u.params.source == "tag") {
            Tag tag = tagRepo.getById(u.params.feedId)
            if (!tag || tag.site != owner) {
                return;
            }
            return pageRepo.drafts(tag)
        } else {
            PageType type = PageType.getByName(u.params.source)
                drafts = pageRepo.drafts(owner, type)
        }
        drafts
    }

    private FeedQuery<Page> feedHelper(final String source, final String feedId, final Site owner, UnitVM unitVM=null) {
        if (source == "all") {
            return pageRepo.feed(owner)
        } else if (source == "tag") {
            Tag tag = tagRepo.getById(feedId)
            if (!tag || tag.site != owner) {
                return;
            }
            if(unitVM) {
                unitVM.title = tag.displayName
            }
            return pageRepo.feed(tag)
        } else {
            PageType type = PageType.getByName(source)
            if(unitVM && !unitVM.title) {
                unitVM.title = i18n.m("pageType.s."+type.name)
            }
            return pageRepo.feed(owner, type)
        }
    }

    @Override
    void setContentFile(ContentHolder unit, FileInfo fileInfo) {
    }

    @Override
    boolean isContentFileSupported(FileInfo info) {
        return false
    }

    @Override
    void saveContent(ContentHolder unit) {
    }

    @Override
    void deleteContent(ContentHolder unit) {
    }
}
