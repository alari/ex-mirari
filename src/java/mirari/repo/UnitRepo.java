package mirari.repo;

import mirari.ko.UnitViewModel;
import mirari.model.Page;
import mirari.model.Unit;
import ru.mirari.infra.mongo.Repo;

/**
 * @author alari
 * @since 1/4/12 4:18 PM
 */
public interface UnitRepo extends Repo<Unit> {
    public Unit buildFor(UnitViewModel viewModel, Page page);
    public Unit getUnitForType(String type);
}
