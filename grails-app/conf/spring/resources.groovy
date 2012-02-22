import grails.util.Environment
import mirari.model.unit.content.external.RussiaRuContentStrategy
import mirari.model.unit.content.external.YouTubeContentStrategy
import mirari.model.unit.content.internal.HtmlContentStrategy
import mirari.model.unit.content.internal.ImageContentStrategy
import mirari.model.unit.content.internal.SoundContentStrategy
import mirari.util.ApplicationContextHolder
import mirari.util.I18n
import mirari.util.link.SiteLinkGenerator
import mirari.util.workaround.MockTransactionManager
import ru.mirari.infra.mongo.MorphiaDriver
import ru.mirari.infra.security.UserDetailsService
import ru.mirari.infra.security.dao.SecurityCodeDao
import mirari.dao.*
import mirari.model.unit.inners.impl.AnyInnersStrategy
import mirari.model.unit.inners.impl.EmptyInnersStrategy
import mirari.model.unit.inners.impl.TypedInnersStrategy
import mirari.model.unit.content.internal.TextContentStrategy
import ru.mirari.infra.file.FileStorageHolder
import ru.mirari.infra.file.LocalFileStorage
import ru.mirari.infra.file.S3FileStorage
import mirari.event.EventMediator
import mirari.model.unit.content.internal.RenderInnersContentStrategy
import mirari.event.LoggingEventListener
import mirari.model.page.thumb.PageAvatarThumbChange
import mirari.model.page.thumb.PageInnerThumbChange
import mirari.model.page.thumb.PageOwnerThumbChange
import ru.mirari.infra.mail.MailSendListenerBean
import mirari.model.disqus.PageDiscoveryChangeListener
import mirari.model.site.feedevents.PagePublishedFeedEvent
import mirari.model.unit.content.internal.FeedContentStrategy

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

    pageFeedRepo(PageFeedDAO)

    tagRepo(TagDao)

    commentRepo(CommentDao)
    replyRepo(ReplyDao)

    avatarRepo(AvatarDao)

    // Content strategies
    russiaRuContentStrategy(RussiaRuContentStrategy)
    youTubeContentStrategy(YouTubeContentStrategy)
    htmlContentStrategy(HtmlContentStrategy)
    textContentStrategy(TextContentStrategy)
    imageContentStrategy(ImageContentStrategy)
    soundContentStrategy(SoundContentStrategy)
    renderInnersContentStrategy(RenderInnersContentStrategy)
    feedContentStrategy(FeedContentStrategy)

    // Inners strategies
    anyInnersStrategy(AnyInnersStrategy)
    emptyInnersStrategy(EmptyInnersStrategy)
    typedInnersStrategy(TypedInnersStrategy)

    // Events
    loggingEventListener(LoggingEventListener)
    pageAvatarThumbChange(PageAvatarThumbChange)
    pageInnerThumbChange(PageInnerThumbChange)
    pageOwnerThumbChange(PageOwnerThumbChange)
    mailSendListener(MailSendListenerBean)
    pageDiscoveryChangeListener(PageDiscoveryChangeListener)
    // Feed Events
    pagePublishedFeedEvent(PagePublishedFeedEvent)
    eventMediator(EventMediator) { bean ->
        bean.factoryMethod = 'getInstance'
        listeners = [
                ref("loggingEventListener"),
                // page thumb changes
                ref("pageAvatarThumbChange"),
                ref("pageInnerThumbChange"),
                ref("pageOwnerThumbChange"),
                // page discovery to disqus discovery
                ref("pageDiscoveryChangeListener"),
                // sendmail
                ref("mailSendListener"),
                // feed events
                ref("pagePublishedFeedEvent")
        ]
    }

    // Misc
    i18n(I18n)
    localeResolver(org.springframework.web.servlet.i18n.SessionLocaleResolver) {
                defaultLocale = new Locale("ru","RU")
                java.util.Locale.setDefault(defaultLocale)
    }

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
