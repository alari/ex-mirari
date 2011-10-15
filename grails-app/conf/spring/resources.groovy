import mirari.morphia.MorphiaDriver
import mirari.morphia.sec.RegistrationCodeDAO
import mirari.morphia.subject.SubjectDAO
import mirari.morphia.subject.SubjectInfoDAO
import mirari.morphia.subject.PersonDAO
import mirari.UserDetailsService
import mirari.I18n

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
