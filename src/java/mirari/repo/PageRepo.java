package mirari.repo;

import mirari.model.Page;
import mirari.model.Site;
import mirari.model.Tag;
import mirari.model.image.CommonImage;
import mirari.model.page.PageType;
import ru.mirari.infra.feed.FeedQuery;
import ru.mirari.infra.persistence.Repo;

/**
 * @author alari
 * @since 1/4/12 4:10 PM
 */
public interface PageRepo extends Repo<Page> {
    public Page getByName(Site site, String name);

    public FeedQuery<Page> feed(Site site);

    public FeedQuery<Page> feed(Site site, PageType type);

    public FeedQuery<Page> feed(Tag tag);

    public FeedQuery<Page> drafts(Site site);

    public FeedQuery<Page> drafts(Site site, PageType type);

    public FeedQuery<Page> drafts(Tag tag);

    public void setPageDraft(Page page, boolean draft);

    public void setImage(Page page, CommonImage image, int origin);

    public void setImage(Site owner, CommonImage image);

    public void setImage(Site owner);
}
