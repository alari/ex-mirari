package mirari.act

import mirari.I18n
import mirari.ServiceResponse

import ru.mirari.infra.security.RegisterCommand
import ru.mirari.infra.security.ResetPasswordCommand
import org.apache.log4j.Logger
import ru.mirari.infra.security.AccountRepository
import ru.mirari.infra.security.SecurityCodeRepository
import ru.mirari.infra.security.Account
import ru.mirari.infra.security.SecurityCode
import ru.mirari.infra.security.Authority
import mirari.morphia.site.Profile
import mirari.morphia.Site

class RegistrationActService {
    static transactional = false
    private Logger log = Logger.getLogger(getClass())

    def springSecurityService
    def mailSenderService
    I18n i18n

    AccountRepository accountRepository
    SecurityCodeRepository securityCodeRepository
    Site.Dao siteDao

    def grailsApplication

    private ConfigObject getConf() {
        grailsApplication.config
    }

    /**
     * Tries to register a user
     *
     * @param command
     * @return
     */
    ServiceResponse handleRegistration(RegisterCommand command) {
        ServiceResponse resp = new ServiceResponse()
        if (command.hasErrors()) {
            return resp.error("register.error.commandValidationFailed")
        }

        Account account = new Account(
                email: command.email, password: command.password, accountLocked: true, enabled: true)
        accountRepository.save(account)

        if (!account.id) {
            log.error "account not saved"
            return resp.error("register.error.userNotSaved")
        }
        
        Profile profile = new Profile(
                account: account,
                name: command.name,
                displayName: command.displayName
        )
        siteDao.save(profile)
        if(!profile.id) {
            accountRepository.delete(account)
            return resp.error("register.error.profileNotSaved")
        }
        
        SecurityCode code = new SecurityCode(account: account)
        securityCodeRepository.save(code)

        sendRegisterEmail(account, code.token)
        return resp.model(emailSent: true, token: code.token).success()
    }

    /**
     * Verifies registration (user email) by token
     *
     * @param token
     * @return
     */
    ServiceResponse verifyRegistration(String token) {
        ServiceResponse result = new ServiceResponse().redirect(conf.grails.mirari.sec.url.defaultTarget)

        def code = token ? securityCodeRepository.getByToken(token) : null
        if (!code) {
            return result.error("register.error.badCode")
        }

        Account account = code.account

        if (!account || !account.id) {
            return result.error("register.error.userNotFound")
        }
        setDefaultRoles(account)
        accountRepository.save(account)
        
        securityCodeRepository.delete(code)

        if (result.alertCode) {
            return result
        }

        if (!account) {
            return result.error("register.error.badCode")
        }

        springSecurityService.reauthenticate account.email
        return result.redirect(conf.grails.mirari.sec.url.emailVerified).success("register.complete")
    }

    /**
     * Sends an forgot-password email
     *
     * @param email
     * @return
     */
    ServiceResponse handleForgotPassword(String emailOrName) {
        ServiceResponse response = new ServiceResponse()
        if (!emailOrName) {
            return response.warning('register.forgotPassword.username.missing')
        }

        Account account = accountRepository.getByEmail(emailOrName)
        if(!account) {
            Site profile = siteDao.getByName(emailOrName)
            if(profile && profile instanceof Profile) {
                account = ((Profile)profile).account
            }
        }
        
        if (!account) {
            return response.error('register.forgotPassword.user.notFound')
        }
        
        SecurityCode code = new SecurityCode(account: account)
        securityCodeRepository.save(code)

        sendForgotPasswordEmail(account, code.token)
        return response.model(emailSent: true, token: code.token).info()
    }

    /**
     * Resets the password for user
     *
     * @param token
     * @param command
     * @param requestMethod
     * @return
     */
    ServiceResponse handleResetPassword(String token, ResetPasswordCommand command, String requestMethod) {
        SecurityCode code = token ? securityCodeRepository.getByToken(token) : null
        if (!code) {
            return new ServiceResponse().redirect(conf.grails.mirari.sec.url.defaultTarget).error('register.resetPassword.badCode')
        }

        if (!requestMethod.equalsIgnoreCase("post")) {
            return new ServiceResponse().model(token: token, command: new ResetPasswordCommand()).warning()
        }

        command.email = code.email
        command.validate()

        if (command.hasErrors()) {
            return new ServiceResponse().model(token: token, command: command).error()
        }
        
        Account account = code.account

        if (!account) {
            return new ServiceResponse().model(token: token, command: new ResetPasswordCommand()).warning('register.forgotPassword.user.notFound')
        }
        account.password = command.password

        // validate user account if it wasn't before
        if (user.accountLocked && user.authorities.size() == 0) {
            setDefaultRoles(account)
        }
        accountRepository.save(account)
        securityCodeRepository.delete(code)


        springSecurityService.reauthenticate account.email

        return new ServiceResponse().redirect(
                conf.grails.mirari.sec.url.passwordResetted
                ?: conf.grails.mirari.sec.url.defaultTarget
        ).success('register.resetPassword.success')
    }

    private setDefaultRoles(Account account) {
        account.accountLocked = false
        List defaultRoleNames = conf.grails.mirari.sec.defaultRoleNames
        account.authorities = defaultRoleNames.collect {new Authority(it.toString())}
    }

    /**
     * Sending register email routine
     *
     * @param person
     * @param token
     * @return
     */
    private boolean sendRegisterEmail(Account account, String token) {
        mailSenderService.putMessage(
                to: account.email,
                subject: i18n."register.confirm.emailSubject",
                view: "/mail-messages/confirmEmail",
                model: [username: account.email, token: token]
        )
        true
    }

    /**
     * Sending password reminder email routine
     *
     * @param person
     * @param token
     * @return
     */
    private boolean sendForgotPasswordEmail(Account account, String token) {
        mailSenderService.putMessage(
                to: account.email,
                subject: i18n."register.forgotPassword.emailSubject",
                view: "/mail-messages/forgotPassword",
                model: [username: account.email, token: token]
        )
        true
    }
}
