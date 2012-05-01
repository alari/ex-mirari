package mirari.repo;

import mirari.model.Follow;
import mirari.model.Site;
import ru.mirari.infra.persistence.Repo;

import java.util.List;

/**
 * @author alari
 * @since 3/31/12 1:26 AM
 */
public interface FollowRepo extends Repo<Follow>{
    void add(Site follower, Site target);
    boolean exists(Site follower, Site target);
    void remove(Site follower, Site target);
    void remove(Site site);
    
    List<Site> followers(Site target);
    List<Site> targets(Site follower);
}
