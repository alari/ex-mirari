package mirari.repo;

import mirari.model.Page;
import mirari.model.Site;
import mirari.model.Tag;
import ru.mirari.infra.persistence.Repo;

/**
 * @author alari
 * @since 1/13/12 4:56 PM
 */
public interface TagRepo extends Repo<Tag> {
    public Tag getByDisplayNameAndSite(String displayName, Site site);

    public Iterable<Tag> listBySite(Site site);

    void updateByPage(Page page);

    Tag getByPage(final Page page);
}
