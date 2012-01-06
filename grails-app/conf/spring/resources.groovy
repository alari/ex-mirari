import grails.util.Environment
import mirari.model.strategy.content.external.RussiaRuContentStrategy
import mirari.model.strategy.content.external.YouTubeContentStrategy
import mirari.model.strategy.content.internal.HtmlContentStrategy
import mirari.model.strategy.content.internal.ImageContentStrategy
import mirari.model.strategy.content.internal.SoundContentStrategy
import mirari.util.ApplicationContextHolder
import mirari.util.I18n
import mirari.util.SiteLinkGenerator
import mirari.util.workaround.MockTransactionManager
import ru.mirari.infra.mongo.MorphiaDriver
import ru.mirari.infra.security.UserDetailsService
import ru.mirari.infra.security.dao.SecurityCodeDao
import mirari.dao.*
import mirari.model.strategy.inners.impl.AnyInnersStrategy
import mirari.model.strategy.inners.impl.EmptyInnersStrategy
import mirari.model.strategy.inners.impl.TypedInnersStrategy

// Place your Spring DSL code here
beans = {
    // security
    userDetailsService(UserDetailsService)
    securityCodeRepo(SecurityCodeDao)
    accountRepo(AccountDao)

    siteRepo(SiteDao)
    profileRepo(ProfileDao)
    
    // Units
    unitRepo(UnitDao)
    unitContentRepo(UnitContentDao)

    pageRepo(PageDao)

    // Content strategies
    russiaRuContentStrategy(RussiaRuContentStrategy)
    youTubeContentStrategy(YouTubeContentStrategy)
    htmlContentStrategy(HtmlContentStrategy)
    imageContentStrategy(ImageContentStrategy)
    soundContentStrategy(SoundContentStrategy)

    // Inners strategies
    anyInnersStrategy(AnyInnersStrategy)
    emptyInnersStrategy(EmptyInnersStrategy)
    typedInnersStrategy(TypedInnersStrategy)


    // Misc
    i18n(I18n)
    avatarRepo(AvatarDao)
    mainPortalHost(String, Environment.isDevelopmentMode() ? "mirari.loc" : "mirari.ru")
    
    applicationContextHolder(ApplicationContextHolder) { bean ->
        bean.factoryMethod = 'getInstance'
    }
    transactionManager(MockTransactionManager)

    grailsLinkGenerator(SiteLinkGenerator, "", "/")

    // Morphia
    morphiaDriver(MorphiaDriver)
}
