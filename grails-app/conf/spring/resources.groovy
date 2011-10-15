import mirari.I18n
import mirari.UserDetailsService
import mirari.morphia.MorphiaDriver
import mirari.morphia.sec.RegistrationCodeDAO
import mirari.morphia.subject.PersonDAO
import mirari.morphia.subject.SubjectDAO
import mirari.morphia.subject.SubjectInfoDAO

// Place your Spring DSL code here
beans = {
  morphiaDriver(MorphiaDriver)
  userDetailsService(UserDetailsService)
  i18n(I18n)

  registrationCodeDao(RegistrationCodeDAO)

  subjectDao(SubjectDAO)
  subjectInfoDao(SubjectInfoDAO)
  personDao(PersonDAO)

}
