package mirari.repo;

import mirari.model.Account;
import mirari.model.site.Profile;
import ru.mirari.infra.persistence.Repo;

/**
 * @author alari
 * @since 1/4/12 4:21 PM
 */
public interface ProfileRepo extends Repo<Profile>{
    public Iterable<Profile> listByAccount(Account account);
}
