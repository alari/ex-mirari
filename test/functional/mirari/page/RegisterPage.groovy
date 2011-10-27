package mirari.page

import geb.Page

/**
 * @author Dmitry Kurinskiy
 * @since 31.08.11 13:11
 */
class RegisterPage extends Page{
  static url = "x.register"
  static at = { $("#test-page").text() == "register:index" }

  static content = {
    form {$("form[name='registerForm']")}

    inputName {form.find("input[name='name']")}
    inputEmail {$("input", name: "email")}
    inputPwd {$("input", name: "password")}
    inputPwd2 {$("input", name: "password2")}
    submit {$("input[type=submit]")}

    verifyLink{form.find("a.test.verify-registration")}
  }
}
