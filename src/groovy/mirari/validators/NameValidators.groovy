package mirari.validators

/**
 * @author alari
 * @since 10/28/11 1:55 PM
 */
class NameValidators {
    final static public String MATCHER = /^[a-zA-Z0-9][-._a-zA-Z0-9]{0,14}[a-zA-Z0-9]$/

    final static public Map CONSTRAINT_MATCHES = [matches: MATCHER]
}
