import mirari.ApplicationContextHolder
import mirari.I18n
import mirari.morphia.Unit
import mirari.morphia.unit.single.TextUnit
import mirari.morphia.Page
import mirari.workaround.MockTransactionManager
import ru.mirari.infra.security.UserDetailsService
import ru.mirari.infra.security.SecurityCode
import mirari.morphia.Account
import mirari.morphia.Site
import mirari.morphia.site.Profile
import mirari.morphia.Avatar
import grails.util.Environment

// Place your Spring DSL code here
beans = {
    // security
    userDetailsService(UserDetailsService)
    securityCodeRepository(SecurityCode.Dao)
    accountRepository(Account.Dao)

    siteDao(Site.Dao)
    profileDao(Profile.Dao)
    
    // Units
    unitDao(Unit.Dao)
    textUnitContentDao(TextUnit.Content.Dao)

    pageDao(Page.Dao)

    // Misc
    i18n(I18n)
    avatarDao(Avatar.Dao)
    mainPortalHost(String, Environment.isDevelopmentMode() ? "mirari.loc" : "mirari.ru")
    
    applicationContextHolder(ApplicationContextHolder) { bean ->
        bean.factoryMethod = 'getInstance'
    }
    transactionManager(MockTransactionManager)
}
