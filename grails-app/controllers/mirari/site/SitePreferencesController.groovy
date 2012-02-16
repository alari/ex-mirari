package mirari.site

import grails.converters.deep.JSON
import grails.plugins.springsecurity.Secured
import mirari.UtilController
import mirari.model.Account
import mirari.model.avatar.Avatar
import mirari.model.Site
import mirari.model.Tag
import mirari.repo.AccountRepo
import mirari.repo.PageRepo
import mirari.repo.TagRepo
import mirari.util.ServiceResponse
import mirari.util.validators.NameValidators

class SitePreferencesController extends UtilController {

    PageRepo pageRepo
    def avatarService
    def rightsService
    AccountRepo accountRepo
    TagRepo tagRepo

    @Typed
    def tagsAutocomplete() {
        Iterable<Tag> tags = tagRepo.listBySite(_site)
        List<String> strings = []
        for (Tag t: tags) {
            strings.add t.displayName
        }

        render strings as JSON
    }

    @Secured("ROLE_USER")
    def preferences() {
        if (hasNoRight(rightsService.canAdmin(_site))) return;
        [
                profiles: siteRepo.listByAccount(_account),
                isMain: _profile == _site
        ]
    }

    @Secured("ROLE_USER")
    def setFeedBurner(FeedBurnerCommand cmd) {
        if (hasNoRight(rightsService.canAdmin(_site))) return;
        if (cmd.hasErrors()) {
            errorCode = "Invalid feedburner name: " + cmd.feedBurnerName.encodeAsHTML()
        } else {
            Site site = _site
            site.feedBurnerName = cmd.feedBurnerName
            siteRepo.save(site)
        }
        redirect action: "preferences", params: [siteName: _siteName]
    }

    @Secured("ROLE_USER")
    def uploadAvatar() {
        if (hasNoRight(rightsService.canAdmin(_site))) return;

        if (request.post) {
            def f = request.getFile('avatar')
            renderJson avatarService.uploadHolderAvatar(f, _site, siteRepo)
        }
    }

    @Secured("ROLE_USER")
    def changeDisplayName(ChangeDisplayNameCommand command) {
        if (hasNoRight(rightsService.canAdmin(_site))) return;

        Site site = _site
        alert setDisplayName(command, site)

        renderAlerts()

        render template: "changeDisplayName", model: [site: _site, changeDisplayNameCommand: command]
    }

    @Secured("ROLE_USER")
    def changeName(ChangeNameCommand command) {
        if (hasNoRight(rightsService.canAdmin(_site))) return;

        Site site = _site

        if (command.hasErrors()) {
            errorCode = "Неверный формат адреса (имени) сайта"
        } else if (siteRepo.nameExists(command.name)) {
            errorCode = "Название (адрес) сайта должно быть уникально"
        } else {
            site.name = command.name
            siteRepo.save(site)
            if (site.name == command.name) {
                successCode = "Успешно!"
            }
        }
        redirect url: site.getUrl()
    }

    @Secured("ROLE_USER")
    def makeMain() {
        if (hasNoRight(rightsService.canAdmin(_site))) return;
        // TODO: move to services
        if (_site.isProfileSite()) {
            Account account = _account
            account.mainProfile = _site
            accountRepo.save(account)
        }
        redirect uri: _site.getUrl()
    }

    private ServiceResponse setDisplayName(ChangeDisplayNameCommand command, Site site) {
        if (command.hasErrors()) {
            return new ServiceResponse().error("personPreferences.changeDisplayName.error")
        }
        site.displayName = command.displayName
        siteRepo.save(site)

        if (site.displayName == command.displayName) {
            new ServiceResponse().success("personPreferences.changeDisplayName.success")
        } else {
            new ServiceResponse().error("personPreferences.changeDisplayName.error")
        }
    }
}

class FeedBurnerCommand {
    String feedBurnerName

    static constraints = {
        feedBurnerName blank: true, nullable: true, matches: /^[a-zA-Z0-9]+$/
    }
}

class ChangeDisplayNameCommand {
    String displayName

    static constraints = {
        displayName minSize: 2, maxSize: 20, blank: true, nullable: true
    }
}

class ChangeNameCommand {
    String name

    static constraints = {
        name NameValidators.CONSTRAINT_MATCHES
    }
}