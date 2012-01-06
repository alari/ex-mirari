@Typed package mirari.model.strategy.inners

/**
 * @author alari
 * @since 1/6/12 2:26 PM
 */
public enum InnersPolicy {
    NONE(NoneApplyInnersStrategy),
    ALL(AllApplyInnersStrategy),
    TYPED(TypedApplyInnersStrategy);

    final private InnersStrategy strategy

    InnersPolicy(Class<? extends InnersStrategy> strategy) {
        this.strategy = strategy.newInstance()
    }

    InnersStrategy getStrategy() {
        strategy
    }
}