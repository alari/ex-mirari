@Typed package mirari.model.strategy.inners

import mirari.model.strategy.inners.impl.AnyInnersStrategy
import mirari.model.strategy.inners.impl.EmptyInnersStrategy
import mirari.model.strategy.inners.impl.TypedInnersStrategy
import mirari.util.ApplicationContextHolder
import org.springframework.context.ApplicationContextAware

/**
 * @author alari
 * @since 1/6/12 2:26 PM
 */
public enum InnersPolicy{
    EMPTY("empty"),
    ANY("any"),
    TYPED("typed");

    private InnersStrategy strategy
    final private String name

    private InnersPolicy(String name) {
        this.name = name
    }

    InnersStrategy getStrategy() {
        (InnersStrategy)ApplicationContextHolder.getBean(name.concat("InnersStrategy"))
    }
}