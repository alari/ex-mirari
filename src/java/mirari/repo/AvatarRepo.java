package mirari.repo;

import mirari.model.Avatar;
import ru.mirari.infra.mongo.Repo;

/**
 * @author alari
 * @since 1/4/12 4:09 PM
 */
public interface AvatarRepo extends Repo<Avatar>{
    public Avatar getByName(String name);
}