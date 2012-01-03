package mirari.site

import mirari.morphia.Page
import mirari.ServiceResponse
import mirari.morphia.Avatar
import mirari.morphia.Site
import grails.plugins.springsecurity.Secured
import mirari.morphia.site.Profile
import mirari.morphia.Account
import mirari.validators.NameValidators

class SiteController extends SiteUtilController {

    static defaultAction = "index"

    Page.Dao pageDao
    def avatarService
    def rightsService
    Profile.Dao profileDao
    Account.Dao accountRepository

    def index() {
        String pageNum = params.pageNum ?: "-0-"
        int pg = Integer.parseInt(pageNum.substring(1, pageNum.size()-1))

        Page.FeedQuery feed = pageDao.feed(_site, _profile?.id == _site.id).paginate(pg)

        [
                feed: feed
        ]
    }

    @Secured("ROLE_USER")
    def preferences() {
        if (hasNoRight(rightsService.canAdmin(_site))) return;
        [
                profiles: profileDao.listByAccount(_account),
                isMain: _profile.id == _site.id
        ]
    }

    @Secured("ROLE_USER")
    def setFeedBurner(FeedBurnerCommand cmd) {
        if (hasNoRight(rightsService.canAdmin(_site))) return;
        if (cmd.hasErrors()) {
            errorCode = "Invalid feedburner name: "+cmd.feedBurnerName.encodeAsHTML()
        } else {
            Site site = _site
            site.feedBurnerName = cmd.feedBurnerName
            siteDao.save(site)
        }
        redirect action: "preferences", params: [siteName:_siteName]
    }

    @Secured("ROLE_USER")
    def uploadAvatar() {
        if (hasNoRight(rightsService.canAdmin(_site))) return;

        if (request.post) {
            def f = request.getFile('avatar')
            ServiceResponse resp = avatarService.uploadSiteAvatar(f, _site, siteDao)
            render(
                    [thumbnail: avatarService.getUrl(_profile, Avatar.LARGE),
                            alertCode: resp.alertCode].encodeAsJSON())
        }
    }

    @Secured("ROLE_USER")
    def changeDisplayName(ChangeDisplayNameCommand command){
        if (hasNoRight(rightsService.canAdmin(_site))) return;

        Site site = _site
        alert setDisplayName(command, site)

        renderAlerts()

        render template: "changeDisplayName", model: [site: _profile, changeDisplayNameCommand: command]
    }

    @Secured("ROLE_USER")
    def changeName(ChangeNameCommand command) {
        if (hasNoRight(rightsService.canAdmin(_site))) return;

        Site site = _site
        
        if (command.hasErrors()) {
            errorCode = "Неверный формат адреса (имени) сайта"
        } else if (siteDao.nameExists(command.name)) {
            errorCode = "Название (адрес) сайта должно быть уникально"
        } else {
            site.name = command.name
            siteDao.save(site)
            if (site.name == command.name) {
                successCode = "Успешно!"
            }
        }
        redirect uri: site.getUrl(action: "preferences")
    }

    @Secured("ROLE_USER")
    def makeMain(){
        if (hasNoRight(rightsService.canAdmin(_site))) return;
        // TODO: move to services
        if (_site instanceof Profile) {
            Account account = _account
            account.mainProfile = _site
            accountRepository.save(account)
        }
        redirect uri: _site.getUrl(action: "preferences")
    }

    private ServiceResponse setDisplayName(ChangeDisplayNameCommand command, Site site) {
        if (command.hasErrors()) {
            return new ServiceResponse().error("personPreferences.changeDisplayName.error")
        }
        site.displayName = command.displayName
        siteDao.save(site)

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