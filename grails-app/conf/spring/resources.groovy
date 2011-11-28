import mirari.ApplicationContextHolder
import mirari.I18n
import mirari.infra.UserDetailsService
import mirari.morphia.Space
import mirari.morphia.Unit
import mirari.morphia.sec.RegistrationCode
import mirari.morphia.space.Subject
import mirari.morphia.space.subject.Person
import mirari.morphia.unit.single.TextUnit
import mirari.morphia.Page

// Place your Spring DSL code here
beans = {
    userDetailsService(UserDetailsService)
    i18n(I18n)

    registrationCodeDao(RegistrationCode.Dao)

    subjectDao(Subject.Dao)
    personDao(Person.Dao)

    spaceDao(Space.Dao)
    unitDao(Unit.Dao)

    textUnitContentDao(TextUnit.Content.Dao)

    pageDao(Page.Dao)

    applicationContextHolder(ApplicationContextHolder) { bean ->
        bean.factoryMethod = 'getInstance'
    }
}
