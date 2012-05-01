package mirari.model.unit.inners

import mirari.util.ApplicationContextHolder

/**
 * @author alari
 * @since 1/6/12 2:26 PM
 */
public enum InnersPolicy {
    EMPTY("empty"),
    ANY("any"),
    COMPOUND("compound");

    private InnersStrategy strategy
    final private String name

    private InnersPolicy(String name) {
        this.name = name
    }

    InnersStrategy getStrategy() {
        if(strategy == null) {
            synchronized (this){
                if(strategy == null) {
                    strategy = (InnersStrategy) ApplicationContextHolder.getBean(name.concat("InnersStrategy"))
                }
            }
        }
        strategy
    }
}