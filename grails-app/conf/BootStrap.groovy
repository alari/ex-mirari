
import mirari.repo.SiteRepo
import mirari.util.ApplicationContextHolder
import mirari.model.site.SiteType
import mirari.model.Site
import mirari.AvatarService
import mirari.vm.PageVM
import mirari.vm.UnitVM
import mirari.repo.PageRepo

class BootStrap {
    def init = { servletContext ->
        bootstrapAvatars()
        bootstrapMainPortal()
    }
    def destroy = {
    }

    static bootstrapAvatars() {
        AvatarService avatarService = (AvatarService)ApplicationContextHolder.getBean("avatarService")
        avatarService.uploadDefaultBasics()
    }

    static bootstrapMainPortal() {
        SiteRepo siteRepo = (SiteRepo)ApplicationContextHolder.getBean("siteRepo")

        String mainHost = ApplicationContextHolder.config.mirari.mainPortal.host
        String mainTitle = ApplicationContextHolder.config.mirari.mainPortal.displayName

        if(siteRepo.getByHost(mainHost)) return;

            Site portal = new Site(type: SiteType.PORTAL, host: mainHost, name: mainHost, displayName: mainTitle)
            siteRepo.save(portal)

            List texts = []
            texts.add title: "", text: """
Это &ndash; альфа-версия проекта Мирари. Работоспособность, безопасность, устойчивость, сохранность данных не гарантируются.

Пожалуйста, используйте сайт для тестирования, сообщайте об ошибках и пожеланиях разработчику.

Вы сможете сделать вклад в создание нового нужного сервиса.
"""
            texts.add title: "В чём нужность?", text:"""
Творческие задачи, реализуемые, в том числе, в Сети, можно разделить на несколько областей:

1. *Публикация*. Произведение, творческая работа, часто требует более сложных, специализированных и удобных инструментов и форматов для публикации.
2. *Репрезентация*. Творческую работу нужно правильно подать, организовать восприятие личности, инициативы, коллектива.
3. *Распространение*. Работа должна найти своего адресата &ndash; через подписки, тематические ленты, срезы, традиционные социальные сети.
4. *Коммуникация*. В плане творчества. Хорошо устроенные комментарии, рекомендации, рецензии, обзоры.
5. *Коллаборация*. Тематические сообщества, проекты, конкурсы и тому подобное.

Это &ndash; основные фокусы внимания при разработке проекта Мирари. Вы сможете создать личную страницу,
страницу сообщества, творческого коллектива или инициативы. Вы сможете легко публиковать адекватно оформленные
произведения разных видов, в том числе смешивая их. Вы сможете выгодно презентовать творческие начинания с
разных сторон, в том числе и показав свою личность в разных ипостасях. Вы сможете легко налаживать взаимодействие
своей новой странички с традиционными социальными сетями и другими участниками проекта.

Обратитесь к разработчику, чтобы получить дорожную карту работ и узнать даты релизов.
"""

            PageVM pageVM = portal.index.viewModel
            pageVM.title = "Мирари. Альфа"

            for(Map t in texts.reverse()) {
                UnitVM unitVM = new UnitVM()
                unitVM.type = "text"
                unitVM.title = t.title
                unitVM.params = t

                pageVM.inners.reverse(true)
                pageVM.inners.push(unitVM)
                pageVM.inners.reverse(true)
            }
            portal.index.viewModel = pageVM
            ((PageRepo)ApplicationContextHolder.getBean("pageRepo")).save(portal.index)

    }
}
