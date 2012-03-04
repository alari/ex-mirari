package mirari.dao

import mirari.model.Site
import mirari.model.Tag
import mirari.repo.TagRepo
import org.springframework.beans.factory.annotation.Autowired
import ru.mirari.infra.mongo.BaseDao
import ru.mirari.infra.mongo.MorphiaDriver
import com.google.code.morphia.Key
import mirari.model.Page
import mirari.vm.PageVM
import mirari.model.page.PageType
import mirari.model.site.PageFeed
import mirari.vm.UnitVM
import mirari.repo.PageRepo
import mirari.InitPagesService

/**
 * @author alari
 * @since 1/13/12 6:34 PM
 */
class TagDao extends BaseDao<Tag> implements TagRepo {
    @Autowired InitPagesService initPagesService

    @Autowired
    TagDao(MorphiaDriver morphiaDriver) {
        super(morphiaDriver)
    }

    Tag getByDisplayNameAndSite(String displayName, Site site) {
        createQuery().filter("displayName", displayName).filter("site", site).get()
    }

    @Override
    Iterable<Tag> listBySite(Site site) {
        createQuery().filter("site", site).fetch()
    }

    @Override
    Tag getByPage(final Page page) {
        createQuery().filter("page", page).get()
    }

    @Override
    void updateByPage(final Page page) {
        Tag tag = getByPage(page)
        if(tag && page.title) {
            tag.displayName = page.title
            save(tag)
        }
    }

    Key<Tag> save(Tag tag) {
        Key<Tag> t = super.save(tag)
        if(!tag.page) {
            initPagesService.initTag(tag)
            return super.save(tag)
        }
        t
    }
}
