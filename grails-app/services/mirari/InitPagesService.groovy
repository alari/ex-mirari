package mirari

import mirari.model.Tag
import mirari.repo.PageRepo
import mirari.model.Page
import mirari.vm.PageVM
import mirari.model.page.PageType
import mirari.vm.UnitVM

class InitPagesService {

    static transactional = false

    PageRepo pageRepo

    void initTag(Tag tag) {
        Page page = new Page()
        page.owner = tag.site
        page.site = tag.site

        PageVM pageVM = new PageVM()
        pageVM.draft = false
        pageVM.type = PageType.PAGE
        pageVM.title = tag.displayName

        UnitVM unitVM = new UnitVM()
        unitVM.type = "feed"
        unitVM.params = [
                locked: "1",
                source: "tag",
                num: "24",
                style: "list",
                feedId: tag.stringId
        ]

        pageVM.inners = [unitVM]

        page.viewModel = pageVM
        pageRepo.save page
        tag.page = page
    }
}
