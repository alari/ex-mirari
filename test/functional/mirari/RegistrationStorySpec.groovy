package mirari

import mirari.page.RegisterPage

import mirari.page.RootPage
import geb.spock.GebReportingSpec
import spock.lang.Stepwise

/**
 * @author Dmitry Kurinskiy
 * @since 31.08.11 13:22
 */
@Stepwise
class RegistrationStorySpec extends GebReportingSpec {
  def "come from root"(){
    given:
      to RootPage
    expect:
      at RootPage
    when:
      pageNav.topbar.registerLink.click()
    then:
      at RegisterPage
  }

  def "can sign up"(){
    expect:
      at RegisterPage
      form.size() == 1
    when:
      inputDomain.value( "test" )
      inputEmail.value( "test@123.dl" )
      inputPwd.value( "test123\$" )
      inputPwd2.value( "test123\$" )
      submit.click()
    then:
      verifyLink.size() == 1
  }

  def "verify registration"(){
    when:
      verifyLink.click()
    then:
      at RootPage
      pageNav.topbar.navLogged.size() == 1
      pageNav.topbar.sbjLink.text().size() >= "test".size()
  }

  def "sign out"(){
    when:
      pageNav.topbar.logoutLink.click()
    then:
      at RootPage
      pageNav.topbar.navNotLogged.size() == 1
  }

  //TODO: def "sign on and out registered()"{}

  //TODO: def "forgot password"(){}

  //TODO: def "sign on and out with new password"(){}
}
