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
import mirari.model.unit.inners.impl.CompoundInnersStrategy
import mirari.model.unit.content.internal.TextContentStrategy
import ru.mirari.infra.file.FileStorageHolder
import ru.mirari.infra.file.LocalFileStorage
import ru.mirari.infra.file.S3FileStorage
import mirari.event.EventMediator
import mirari.model.unit.content.internal.RenderInnersContentStrategy
import mirari.event.LoggingEventListener
import mirari.model.image.thumb.PageAvatarThumbChange
import mirari.model.image.thumb.PageInnerThumbChange
import mirari.model.image.thumb.PageOwnerThumbChange
import ru.mirari.infra.mail.MailSendListenerBean
import mirari.model.disqus.PageDiscoveryChangeListener
import mirari.model.site.feedevents.PagePublishedFeedEvent
import mirari.model.unit.content.internal.FeedContentStrategy
import mirari.model.unit.content.internal.PageReferenceContentStrategy
import mirari.event.EventRepo
import mirari.model.digest.listeners.DigestCommentsListener
import mirari.event.EventListenerBean
import mirari.model.digest.listeners.DigestRepliesListener
import mirari.model.digest.NoticeBuilder
import mirari.model.digest.listeners.FollowNewPagesListener
import mirari.model.digest.listeners.FollowerNewListener
import mirari.model.digest.listeners.FollowerNewListener
import mirari.model.unit.content.internal.CompoundContentStrategy

// Place your Spring DSL code here
beans = {
    // security
    userDetailsService(UserDetailsService)
    securityCodeRepo(SecurityCodeDao)
    accountRepo(AccountDao)

    siteRepo(SiteDao)

    followRepo(FollowDao)

    // Units
    unitRepo(UnitDao)
    unitContentRepo(UnitContentDao)

    pageRepo(PageDao)

    pageFeedRepo(PageFeedDAO)

    tagRepo(TagDao)

    commentRepo(CommentDao)
    replyRepo(ReplyDao)

    avatarRepo(AvatarDao)

    noticeRepo(NoticeDao)
    noticeBuilder(NoticeBuilder)

    // Content strategies
    russiaRuContentStrategy(RussiaRuContentStrategy)
    youTubeContentStrategy(YouTubeContentStrategy)
    htmlContentStrategy(HtmlContentStrategy)
    textContentStrategy(TextContentStrategy)
    imageContentStrategy(ImageContentStrategy)
    soundContentStrategy(SoundContentStrategy)
    renderInnersContentStrategy(RenderInnersContentStrategy)
    feedContentStrategy(FeedContentStrategy)
    pageReferenceContentStrategy(PageReferenceContentStrategy)
    compoundContentStrategy(CompoundContentStrategy)

    // Inners strategies
    anyInnersStrategy(AnyInnersStrategy)
    emptyInnersStrategy(EmptyInnersStrategy)
    compoundInnersStrategy(CompoundInnersStrategy)

    
    List<Class<? extends EventListenerBean>> eventListeners = [
            // Console logging (debug)
            LoggingEventListener,
            // page thumb changes
            PageAvatarThumbChange,
            PageInnerThumbChange  ,
            PageOwnerThumbChange   ,
            // sendmail
            MailSendListenerBean    ,
            // page discovery to disqus discovery
            PageDiscoveryChangeListener,

            // Feed Events
            PagePublishedFeedEvent,

            // digest collectors
            DigestCommentsListener,
            DigestRepliesListener,
            FollowNewPagesListener,
            FollowerNewListener,
    ]

    for(Class<? extends EventListenerBean> listener : eventListeners) {
        "${listener.name}_ListenerBean"(listener)
    }


    eventMediator(EventMediator) { bean ->
        bean.factoryMethod = 'getInstance'
        bean.initMethod = 'launch'

        listeners = eventListeners.collect { ref("${it.name}_ListenerBean") }
    }

    // Misc
    i18n(I18n)
    localeResolver(org.springframework.web.servlet.i18n.SessionLocaleResolver) {
                defaultLocale = new Locale("ru","RU")
                java.util.Locale.setDefault(defaultLocale)
    }
    eventRepo(EventRepo)

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
