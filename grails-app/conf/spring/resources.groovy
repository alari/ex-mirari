import mirari.ApplicationContextHolder
import mirari.I18n
import mirari.morphia.Unit
import mirari.morphia.unit.single.TextUnit
import mirari.morphia.Page
import mirari.workaround.MockTransactionManager
import ru.mirari.infra.security.UserDetailsService
import ru.mirari.infra.security.SecurityCode
import ru.mirari.infra.security.Account
import mirari.morphia.Site
import mirari.morphia.site.Profile

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
    
    applicationContextHolder(ApplicationContextHolder) { bean ->
        bean.factoryMethod = 'getInstance'
    }
    transactionManager(MockTransactionManager)
}
