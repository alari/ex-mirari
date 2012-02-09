@Typed package mirari.dao

import com.google.code.morphia.Key
import mirari.model.avatar.Avatar
import mirari.repo.AvatarRepo
import org.springframework.beans.factory.annotation.Autowired
import ru.mirari.infra.mongo.BaseDao
import ru.mirari.infra.mongo.MorphiaDriver

/**
 * @author alari
 * @since 1/4/12 4:36 PM
 */
class AvatarDao extends BaseDao<Avatar> implements AvatarRepo {
    @Autowired
    AvatarDao(MorphiaDriver morphiaDriver) {
        super(morphiaDriver)
    }

    private Map<String, Avatar> basicAvatars

    Avatar getByName(String name) {
        createQuery().filter("name", name).get()
    }

    Avatar getBasic(String name) {
        if (null == basicAvatars) {
            synchronized (this) {
                if (null == basicAvatars) {
                    // Cache basic avatars
                    basicAvatars = [:]
                    for (Avatar basicAvatar: createQuery().filter("basic", true).fetch()) {
                        basicAvatars.put(basicAvatar.name, basicAvatar)
                    }
                }
            }
        }
        basicAvatars.get(name)
    }

    @Override
    Key<Avatar> save(Avatar entity) {
        Key<Avatar> k = super.save(entity)
        if (entity.basic) {
            basicAvatars.put(entity.name, entity)
        }
        k
    }
}