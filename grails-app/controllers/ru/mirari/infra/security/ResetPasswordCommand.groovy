package ru.mirari.infra.security

import mirari.util.validators.PasswordValidators

/**
 * @author Dmitry Kurinskiy
 * @since 27.08.11 22:08
 */
class ResetPasswordCommand {
    String email
    String password
    String password2

    static constraints = {
        password blank: false, minSize: 7, maxSize: 64, validator: PasswordValidators.passwordValidator
        password2 validator: PasswordValidators.password2Validator
    }
}
