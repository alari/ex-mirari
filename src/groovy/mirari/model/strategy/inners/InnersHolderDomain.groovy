package mirari.model.strategy.inners

import mirari.model.Unit

/**
 * @author alari
 * @since 2/9/12 8:15 PM
 */
public interface InnersHolderDomain {
    List<Unit> get_inners()

    void set_inners(List<Unit> inners)

    InnersPolicy get_innersPolicy()
}