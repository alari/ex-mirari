package mirari

import mirari.morphia.subject.Person
import mirari.morphia.subject.PersonDAO
import org.springframework.beans.factory.annotation.Autowired

abstract class UtilController {
  def springSecurityService
  def alertsService
  @Autowired PersonDAO personDao

  protected Person getCurrentPerson() {
    personDao.getById(springSecurityService.principal?.id?.toString())
  }

  protected void setErrorCode(String code) {
    alertsService.error flash, code
  }

  protected void setInfoCode(String code) {
    alertsService.info flash, code
  }

  protected void setSuccessCode(String code) {
    alertsService.success flash, code
  }

  protected void setWarningCode(String code) {
    alertsService.warning flash, code
  }

  protected boolean hasNoRight(boolean rightCheck, String errCode = "permission.denied", String redirectUri = null) {
    if (!rightCheck) {
      errorCode = errCode
      redirect uri: redirectUri ?: "/"
      return true
    }
    false
  }
}
