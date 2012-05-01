package mirari.model.unit.content.internal

import mirari.model.unit.content.ContentHolder
import mirari.vm.UnitVM
import org.springframework.beans.factory.annotation.Autowired
import mirari.SiteService
import mirari.model.Site
import mirari.model.Page
import mirari.repo.PageRepo
import mirari.model.unit.content.ContentData

/**
 * @author alari
 * @since 2/27/12 8:10 PM
 */
class PageReferenceContentStrategy extends InternalContentStrategy{

    @Autowired SiteService siteService
    @Autowired PageRepo pageRepo

    private Page getPage(String url) {
        URL _url = new URL(url)
        Site site = siteService.getByHost(_url.host)
        if(!site) return null
        pageRepo.getByName(site, _url.path.substring(1).decodeURL())
    }
    
    Page getPage(UnitVM unitVM) {
        pageRepo.getById(unitVM.params.id)
    }

    @Override
    boolean isUrlSupported(String url) {
        getPage(url) ? true : false
    }

    @Override
    void buildContentByUrl(ContentHolder unit, String url) {
        Page page = getPage(url)
        if(page) {
            ContentData.REF_PAGE_ID.putTo(unit, page.stringId)
        }
    }
    
    @Override
    void attachContentToViewModel(ContentHolder unit, UnitVM unitViewModel) {
        Page page = pageRepo.getById(ContentData.REF_PAGE_ID.getFrom(unit))
        if(!page) {
            unitViewModel.params = [id: ContentData.REF_PAGE_ID.getFrom(unit)]
            return
        }
        unitViewModel.params = [
                id: page.stringId,
                url: page.url,
                title: page.title,
                ownerName: page?.owner?.displayName,
                ownerUrl: page?.owner?.url,
                thumbSrc: page?.image?.thumbSrc
        ]
    }

    @Override
    void setViewModelContent(ContentHolder unit, UnitVM unitViewModel) {
    }
}
