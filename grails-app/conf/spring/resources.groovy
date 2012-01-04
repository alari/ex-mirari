import mirari.util.ApplicationContextHolder
import mirari.util.I18n
import mirari.util.workaround.MockTransactionManager
import ru.mirari.infra.security.UserDetailsService
import grails.util.Environment
import mirari.util.SiteLinkGenerator
import ru.mirari.infra.mongo.MorphiaDriver
import mirari.dao.AccountDao
import ru.mirari.infra.security.dao.SecurityCodeDao
import mirari.dao.AvatarDao
import mirari.dao.PageDao
import mirari.dao.ProfileDao
import mirari.dao.SiteDao
import mirari.dao.TextUnitContentDao
import mirari.dao.UnitDao

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
    textUnitContentRepo(TextUnitContentDao)

    pageRepo(PageDao)

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
