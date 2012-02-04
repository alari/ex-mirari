package mirari.page

import geb.Page
import mirari.module.PageNavModule

/**
 * @author Dmitry Kurinskiy
 * @since 20.10.11 19:31
 */
class PersonPreferencesPage extends Page{
  static url = "own.personPreferences"
  static at = { $("#test-page").text() == "personPreferences:preferences" }

  static content = {
    pageNav {module PageNavModule}
  }
}
