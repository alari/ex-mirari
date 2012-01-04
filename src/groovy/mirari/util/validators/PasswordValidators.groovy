package mirari.util.validators

/**
 * @author Dmitry Kurinskiy
 * @since 27.08.11 22:10
 */
class PasswordValidators {
    static final passwordValidator = { String password, command ->
        if (command.email && command.name.equals(password)) {
            return 'command.password.error.username'
        }

        /*if (password && password.length() >= 7 && password.length() <= 64 &&
                (!password.matches('^.*\\p{Alpha}.*$') ||
                        !password.matches('^.*\\p{Digit}.*$'))) {
            return 'command.password.error.strength'
        }*/
    }

    static final password2Validator = { value, command ->
        if (command.password != command.password2) {
            return 'command.password2.error.mismatch'
        }
    }
}
