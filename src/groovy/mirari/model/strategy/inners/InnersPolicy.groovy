@Typed package mirari.model.strategy.inners

import mirari.model.strategy.inners.impl.AnyInnersStrategy
import mirari.model.strategy.inners.impl.EmptyInnersStrategy
import mirari.model.strategy.inners.impl.TypedInnersStrategy

/**
 * @author alari
 * @since 1/6/12 2:26 PM
 */
public enum InnersPolicy {
    EMPTY(EmptyInnersStrategy),
    ANY(AnyInnersStrategy),
    TYPED(TypedInnersStrategy);

    final private InnersStrategy strategy

    private InnersPolicy(Class<? extends InnersStrategy> strategy) {
        this.strategy = strategy.newInstance()
    }

    InnersStrategy getStrategy() {
        strategy
    }
}