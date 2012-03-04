package mirari

import com.sun.syndication.io.SyndFeedOutput
import mirari.model.Page
import mirari.model.Site
import mirari.model.Unit
import mirari.model.avatar.Avatar
import mirari.repo.PageRepo
import mirari.repo.SiteRepo
import ru.mirari.infra.feed.FeedQuery
import com.sun.syndication.feed.synd.*

class FeedController extends UtilController {

    SiteRepo siteRepo
    PageRepo pageRepo
    def avatarService

    def site(String id) {
        Site site = siteRepo.getById(id)
        if (isNotFound(site)) return;

        FeedQuery<Page> feedQ = pageRepo.feed(site)

        SyndFeed feed = new SyndFeedImpl();

        feed.setFeedType("atom_1.0");

        feed.setTitle(site.displayName);
        feed.setLink(site.url);
        feed.setDescription("Feed of site: " + site.toString());


        SyndImage image = new SyndImageImpl()
        image.url = avatarService.getUrl(site, Avatar.LARGE)
        image.title = "avatar"
        image.link = avatarService.getUrl(site, Avatar.LARGE)
        feed.setImage(image)



        List<SyndEntry> entries = new ArrayList<SyndEntry>();
        SyndEntry entry;
        SyndContent description;

        feedQ.each {Page p ->
            entry = new SyndEntryImpl();
            entry.title = p.title;
            entry.link = p.url
            entry.publishedDate = p.publishedDate

            if (p.inners.size()) {
                description = new SyndContentImpl();
                description.type = "text/html"
                Unit u = p.inners.first()
                description.value = groovyPageRenderer.render(template: "/unit-render/page".concat(u.type), model: [unit: u])

                entry.description = description
            }

            entries.add(entry);
        }

        feed.entries = entries

        Writer writer = new StringWriter();

        SyndFeedOutput output = new SyndFeedOutput();
        output.output(feed, writer);

        response.contentType = "text/xml; charset=UTF-8"
        render writer
    }
}
