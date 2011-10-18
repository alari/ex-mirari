package mirari

import geb.spock.GebReportingSpec
import spock.lang.Stepwise
import mirari.page.RootPage

/**
 * @author Dmitry Kurinskiy
 * @since 18.10.11 21:55
 */
@Stepwise
class RootPageSpec extends GebReportingSpec {
  def "root page opens"(){
    when:
    to RootPage
    then:
    at RootPage
  }

  def "click on logo leads to root page"(){
    when:
    pageNav.topbar.rootLink.click()
    then:
    at RootPage
  }
}
