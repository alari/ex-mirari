@Typed package mirari.dao

import mirari.model.Avatar
import mirari.repo.AvatarRepo
import org.springframework.beans.factory.annotation.Autowired
import ru.mirari.infra.mongo.BaseDao
import ru.mirari.infra.mongo.MorphiaDriver

/**
 * @author alari
 * @since 1/4/12 4:36 PM
 */
class AvatarDao extends BaseDao<Avatar> implements AvatarRepo{
    @Autowired AvatarDao(MorphiaDriver morphiaDriver) {
        super(morphiaDriver)
    }

    Avatar getByName(String name) {
        createQuery().filter("name", name).get()
    }
}