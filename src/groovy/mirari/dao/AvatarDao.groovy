@Typed package mirari.dao

import ru.mirari.infra.mongo.BaseDao
import mirari.repo.AvatarRepo
import org.springframework.beans.factory.annotation.Autowired
import ru.mirari.infra.mongo.MorphiaDriver
import mirari.model.Avatar

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