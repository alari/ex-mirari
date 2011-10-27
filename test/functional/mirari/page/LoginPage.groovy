package mirari.page

import geb.Page

/**
 * @author Dmitry Kurinskiy
 * @since 22.10.11 14:20
 */
class LoginPage extends Page {
  static url = "x.login"
  static at = { $("#test-page").text() == "login:auth" }

  static content = {
    form {$("form[name='loginForm']")}

    inputName {form.find("input#username")}
    inputPwd {form.find("input#password")}
    submit {form.find("input[type='submit']")}
  }
}
