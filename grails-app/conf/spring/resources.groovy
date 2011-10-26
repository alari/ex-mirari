import mirari.I18n
import mirari.UserDetailsService
import mirari.morphia.MorphiaDriver
import mirari.morphia.sec.RegistrationCode
import mirari.morphia.subject.Person
import mirari.morphia.subject.Subject
import mirari.morphia.subject.SubjectInfo
import mirari.util.ConfigReader
import mirari.util.file.LocalFileStorage
import mirari.util.file.S3FileStorage

// Place your Spring DSL code here
beans = {
  configReader(ConfigReader)

  morphiaDriver(MorphiaDriver)
  userDetailsService(UserDetailsService)
  i18n(I18n)

  registrationCodeDao(RegistrationCode.Dao)

  subjectDao(Subject.Dao)
  subjectInfoDao(SubjectInfo.Dao)
  personDao(Person.Dao)


  s3FileStorage( S3FileStorage )
  localFileStorage( LocalFileStorage )
}
