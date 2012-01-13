package mirari.repo;

import mirari.model.Site;
import mirari.model.Tag;
import ru.mirari.infra.persistence.Repo;

/**
 * @author alari
 * @since 1/13/12 4:56 PM
 */
public interface TagRepo extends Repo<Tag>{
    public Tag getByDisplayNameAndSite(String displayName, Site site);
}
