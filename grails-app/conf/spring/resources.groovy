import mirari.I18n
import mirari.infra.UserDetailsService
import mirari.morphia.MorphiaDriver
import mirari.morphia.Space
import mirari.morphia.Unit
import mirari.morphia.sec.RegistrationCode
import mirari.morphia.space.Subject
import mirari.morphia.space.subject.Person
import mirari.util.ConfigReader

// Place your Spring DSL code here
beans = {
    configReader(ConfigReader)

    morphiaDriver(MorphiaDriver)
    userDetailsService(UserDetailsService)
    i18n(I18n)

    registrationCodeDao(RegistrationCode.Dao)

    subjectDao(Subject.Dao)
    personDao(Person.Dao)

    spaceDao(Space.Dao)
    unitDao(Unit.Dao)
}
