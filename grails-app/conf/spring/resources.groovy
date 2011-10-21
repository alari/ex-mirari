import mirari.I18n
import mirari.UserDetailsService
import mirari.morphia.MorphiaDriver
import mirari.morphia.sec.RegistrationCodeDAO
import mirari.morphia.subject.PersonDAO
import mirari.morphia.subject.SubjectDAO
import mirari.morphia.subject.SubjectInfoDAO
import mirari.util.file.LocalFileStorage
import mirari.util.ConfigReader
import mirari.util.file.S3FileStorage

// Place your Spring DSL code here
beans = {
  configReader(ConfigReader)

  morphiaDriver(MorphiaDriver)
  userDetailsService(UserDetailsService)
  i18n(I18n)

  registrationCodeDao(RegistrationCodeDAO)

  subjectDao(SubjectDAO)
  subjectInfoDao(SubjectInfoDAO)
  personDao(PersonDAO)


  s3FileStorage( S3FileStorage )
  localFileStorage( LocalFileStorage )
}
