package mirari.model.strategy.inners

/**
 * @author alari
 * @since 1/6/12 2:57 PM
 */
class NoneApplyInnersStrategy extends InnersStrategy{
    @Override
    void attachInnersToViewModel(InnersHolder holder, mirari.ko.ViewModel vm) {
    }

    @Override
    boolean attachInner(InnersHolder holder, mirari.model.Unit unit) {
        false
    }
}
