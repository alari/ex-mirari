package mirari.dao

import ru.mirari.infra.mongo.BaseDao
import mirari.model.Follow
import mirari.repo.FollowRepo
import ru.mirari.infra.mongo.MorphiaDriver
import org.springframework.beans.factory.annotation.Autowired
import mirari.model.Site

/**
 * @author alari
 * @since 3/31/12 1:28 AM
 */
class FollowDao extends BaseDao<Follow> implements FollowRepo{
    @Autowired
    FollowDao(MorphiaDriver morphiaDriver) {
        super(morphiaDriver)
    }

    @Override
    void add(final Site follower, final Site target) {
        if(!exists(follower, target)) {
            save new Follow(follower: follower, target: target)
        }
    }

    @Override
    boolean exists(final Site follower, final Site target) {
        createQuery().filter("follower", follower).filter("target", target).countAll() > 0
    }

    @Override
    void remove(final Site follower, Site target) {
        deleteByQuery(
            createQuery().filter("follower", follower).filter("target", target)
        )
    }

    @Override
    List<Site> followers(final Site target) {
        List<Site> followers = []
        for( Follow f in createQuery().filter("target", target).retrievedFields(true, "follower").fetch()) {
            followers.add f.follower
        }
        followers
    }

    @Override
    List<Site> targets(final Site follower) {
        List<Site> targets = []
        for( Follow f in createQuery().filter("follower", follower).retrievedFields(true, "target").fetch()) {
            targets.add f.follower
        }
        targets
    }

    @Override
    void remove(final Site site) {
        deleteByQuery createQuery().filter("target", site)
        deleteByQuery createQuery().filter("follower", site)
    }
}
