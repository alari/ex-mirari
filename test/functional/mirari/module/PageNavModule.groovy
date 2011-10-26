package mirari.module

import geb.Module

/**
 * @author Dmitry Kurinskiy
 * @since 18.10.11 22:11
 */
class PageNavModule extends Module{
  static content = {
    topbar {module TopbarModule}
  }
}

class TopbarModule extends Module {

  static base = { $("div.topbar div.topbar-inner div.container") }

  static content = {
    rootLink { $("h3 a") }
    navLogged { $("ul.nav.secondary-nav.logged-in") }
    navNotLogged { $("ul.nav.secondary-nav", 'class': iNotContains(~/logged-in/)) }

    registerLink { navNotLogged.find("li a", 0) }
    loginLink { navNotLogged.find("li a", 1) }

    settingsDropdown { navLogged.find("a.dropdown-toggle") }
    sbjLink { navLogged.find("li a", 0) }
    logoutLink { navLogged.find("li a[name='logout']") }
  }
}