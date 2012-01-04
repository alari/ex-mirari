package mirari.repo;

import mirari.ko.UnitViewModel;
import mirari.model.Page;
import mirari.model.Unit;
import mirari.model.face.UnitsContainer;
import ru.mirari.infra.mongo.Repo;

import java.util.List;
import java.util.Map;

/**
 * @author alari
 * @since 1/4/12 4:18 PM
 */
public interface UnitRepo extends Repo<Unit> {
    public Unit buildFor(UnitViewModel viewModel, Page page);

    public void attachUnits(UnitsContainer outer, List<UnitViewModel> _inners, Page page, Map<String, Unit> oldUnits);
    public void attachUnits(UnitsContainer outer, List<UnitViewModel> _inners, Page page);

    public Map<String, Unit> collectUnits(UnitsContainer outer);

    public Unit getUnitForType(String type);
}
