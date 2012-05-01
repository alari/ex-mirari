package mirari.model.unit.content

/**
 * @author alari
 * @since 4/6/12 11:53 PM
 */
public enum CompoundType {
    POETRY("poetry",
            CompoundSchema.create()
            .required(ContentPolicy.TEXT)
            .maybe(ContentPolicy.SOUND, 2)
            .maybe(ContentPolicy.IMAGE)
    );

    final String name
    final CompoundSchema schema

    static final Map<String,CompoundType> byName = [:]

    static {
        for(CompoundType t in values()) byName.put(t.name, t)
    }

    static CompoundType get(String name) {
        byName.get(name)
    }

    private CompoundType(String name, CompoundSchema schema) {
        this.name = name
        this.schema = schema
    }
}