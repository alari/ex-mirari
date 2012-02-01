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
import mirari.model.strategy.content.internal.TextContentStrategy
import ru.mirari.infra.file.FileStorageHolder
import ru.mirari.infra.file.LocalFileStorage
import ru.mirari.infra.file.S3FileStorage

// Place your Spring DSL code here
beans = {
    // security
    userDetailsService(UserDetailsService)
    securityCodeRepo(SecurityCodeDao)
    accountRepo(AccountDao)

    siteRepo(SiteDao)
    
    // Units
    unitRepo(UnitDao)
    unitContentRepo(UnitContentDao)

    pageRepo(PageDao)

    tagRepo(TagDao)

    // Content strategies
    russiaRuContentStrategy(RussiaRuContentStrategy)
    youTubeContentStrategy(YouTubeContentStrategy)
    htmlContentStrategy(HtmlContentStrategy)
    textContentStrategy(TextContentStrategy)
    imageContentStrategy(ImageContentStrategy)
    soundContentStrategy(SoundContentStrategy)

    // Inners strategies
    anyInnersStrategy(AnyInnersStrategy)
    emptyInnersStrategy(EmptyInnersStrategy)
    typedInnersStrategy(TypedInnersStrategy)


    // Misc
    i18n(I18n)
    avatarRepo(AvatarDao)

    applicationContextHolder(ApplicationContextHolder) { bean ->
        bean.factoryMethod = 'getInstance'
    }
    transactionManager(MockTransactionManager)

    grailsLinkGenerator(SiteLinkGenerator, "", "/")

    // Morphia
    morphiaDriver(MorphiaDriver)

    // File storage
    s3FileStorage(S3FileStorage)
    localFileStorage(LocalFileStorage)
    fileStorage(FileStorageHolder) {
        storage = ref(Environment.isWarDeployed() ? "s3FileStorage" : "localFileStorage")
    }
}
