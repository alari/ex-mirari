package mirari

import org.codehaus.groovy.grails.web.mapping.DefaultLinkGenerator
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsWebRequest
import javax.servlet.http.HttpServletRequest
import mirari.morphia.Site
import mirari.morphia.Page
import mirari.morphia.Unit

/**
 * @author alari
 * @since 1/3/12 10:07 PM
 */
class SiteLinkGenerator extends DefaultLinkGenerator{
    SiteLinkGenerator(String serverBaseURL, String contextPath) {
        super(serverBaseURL, contextPath)
    }

    SiteLinkGenerator(String serverBaseURL) {
        super(serverBaseURL)
    }

    /**
     * {@inheritDoc }
     * @attr for Site, Page, Unit
     * @attr forSite boolean
     */
    String link(Map attrs, String encoding = 'UTF-8') {
        if(attrs.containsKey("plain")) {
            attrs.remove("plain")
            return super.link(attrs, encoding)
        }
        attrs.params = attrs.params ?: [:]
        def forObject = attrs.remove("for")
        if(!forObject) {
            forObject = attrs.containsKey("forSite") ? request?._site : request?._portal
        }
        super.link(forObject ? fitAttrs(forObject, attrs) : attrs, encoding)
    }

    @Typed
    private Map fitAttrs(Site site, Map args) {
        args.action = args.action ?: ""
        args.controller = args.controller ?: "site"
        args.base = "http://".concat(site.host)
        args
    }

    @Typed
    private Map fitAttrs(Page page, Map args) {
        args.action = args.action ?: ""
        args.controller = args.controller ?: "sitePage"
        args.base = "http://".concat(((Page)page).site.host)
        ((Map)args.params).pageName = page.name ?: "null"
        args
    }

    @Typed
    private Map fitAttrs(Unit unit, Map args) {
        args.action = args.action ?: ""
        args.controller = args.controller ?: "siteUnit"
        args.base = "http://".concat(unit.owner.host)
        ((Map)args.params).id = unit.id.toString()
        args
    }
    
    private HttpServletRequest getRequest() {
        GrailsWebRequest.lookup()?.currentRequest
    }
}
