package mirari.repo;

import mirari.ko.UnitViewModel;
import mirari.model.Page;
import mirari.model.Unit;
import mirari.model.strategy.inners.InnersHolder;
import ru.mirari.infra.persistence.Repo;

/**
 * @author alari
 * @since 1/4/12 4:18 PM
 */
public interface UnitRepo extends Repo<Unit> {
    public void removeEmptyInners(InnersHolder holder);

    public Unit buildFor(UnitViewModel viewModel, Page page);
}
