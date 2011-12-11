package mirari.page

import geb.Page
import mirari.module.PageNavModule

/**
 * @author Dmitry Kurinskiy
 * @since 10/3/11 5:11 PM
 */
class RootPage extends Page{
  static url = ""
  static at = { $("#test-page").text() == "root:index" }

  static content = {
    pageNav {module PageNavModule}
  }
}
