package mirari

import mirari.morphia.Site
import mirari.morphia.Page
import com.sun.syndication.feed.synd.SyndFeedImpl
import com.sun.syndication.feed.synd.SyndFeed
import com.sun.syndication.feed.synd.SyndContentImpl
import com.sun.syndication.feed.synd.SyndEntryImpl
import com.sun.syndication.feed.synd.SyndContent
import com.sun.syndication.feed.synd.SyndEntry
import mirari.morphia.Unit
import com.sun.syndication.io.SyndFeedOutput
import com.sun.syndication.feed.synd.SyndImage
import com.sun.syndication.feed.synd.SyndImageImpl
import mirari.morphia.Avatar

class FeedController extends UtilController{

    Site.Dao siteDao
    Page.Dao pageDao
    def siteLinkService
    def avatarService
    
    def site(String id) {
        Site site = siteDao.getById(id)
        if (isNotFound(site)) return;
        
        Page.FeedQuery feedQ = pageDao.feed(site, false)

        SyndFeed feed = new SyndFeedImpl();

        feed.setFeedType("atom_1.0");

        feed.setTitle(site.displayName);
        feed.setLink(siteLinkService.getUrl(site, [absolute: true]));
        feed.setDescription("Feed of site: "+site.toString());
        
        
        SyndImage image = new SyndImageImpl()
        image.url = avatarService.getUrl(site, Avatar.LARGE)
        image.title = "avatar"
        image.link = avatarService.getUrl(site, Avatar.LARGE)
        feed.setImage(image)



        List<SyndEntry> entries = new ArrayList<SyndEntry>();
        SyndEntry entry;
        SyndContent description;

        feedQ.each {Page p->
            entry = new SyndEntryImpl();
            entry.title = p.title;
            entry.link = siteLinkService.getUrl(p, [absolute: true]);
            entry.publishedDate = p.dateCreated

            if (p.inners.size()) {
                description = new SyndContentImpl();
                description.type = "text/html"
                Unit u = p.inners.first()
                description.value = groovyPageRenderer.render(template: "/unit-render/page".concat(u.type), model: [unit:u])

                entry.description = description
            }

            entries.add(entry);
        }
        
        feed.entries = entries

        Writer writer = new StringWriter();

        SyndFeedOutput output = new SyndFeedOutput();
        output.output(feed,writer);

        response.contentType = "text/xml; charset=UTF-8"
        render writer
    }
}
