package mirari.repo;

import mirari.model.Account;
import mirari.model.Site;
import ru.mirari.infra.persistence.Repo;

/**
 * @author alari
 * @since 1/4/12 4:17 PM
 */
public interface SiteRepo extends Repo<Site> {
    public Site getByName(String name);

    public boolean nameExists(String name);

    public Site getByHost(String host);

    public boolean hostExists(String host);

    Iterable<Site> listByAccount(Account account);
}
