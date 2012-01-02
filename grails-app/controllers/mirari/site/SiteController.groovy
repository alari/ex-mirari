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
    def siteLinkService

    def index() {
        String pageNum = params.pageNum ?: "-0-"
        int pg = Integer.parseInt(pageNum.substring(1, pageNum.size()-1))

        Page.FeedQuery feed = pageDao.feed(currentSite, currentProfile?.id == currentSite.id).paginate(pg)

        [
                feed: feed
        ]
    }

    @Secured("ROLE_USER")
    def preferences() {
        if (hasNoRight(rightsService.canAdmin(currentSite))) return;
        [
                profiles: profileDao.listByAccount(currentAccount),
                isMain: currentProfile.id == currentSite.id
        ]
    }

    @Secured("ROLE_USER")
    def setFeedBurner(FeedBurnerCommand cmd) {
        if (hasNoRight(rightsService.canAdmin(currentSite))) return;
        if (cmd.hasErrors()) {
            errorCode = "Invalid feedburner name: "+cmd.feedBurnerName.encodeAsHTML()
        } else {
            Site site = currentSite
            site.feedBurnerName = cmd.feedBurnerName
            siteDao.save(site)
        }
        redirect action: "preferences", params: [siteName:currentSiteName]
    }

    @Secured("ROLE_USER")
    def uploadAvatar() {
        if (hasNoRight(rightsService.canAdmin(currentSite))) return;

        if (request.post) {
            def f = request.getFile('avatar')
            ServiceResponse resp = avatarService.uploadSiteAvatar(f, currentSite, siteDao)
            render(
                    [thumbnail: avatarService.getUrl(currentProfile, Avatar.LARGE),
                            alertCode: resp.alertCode].encodeAsJSON())
        }
    }

    @Secured("ROLE_USER")
    def changeDisplayName(ChangeDisplayNameCommand command){
        if (hasNoRight(rightsService.canAdmin(currentSite))) return;

        Site site = currentSite
        alert setDisplayName(command, site)

        renderAlerts()

        render template: "changeDisplayName", model: [site: currentProfile, changeDisplayNameCommand: command]
    }

    @Secured("ROLE_USER")
    def changeName(ChangeNameCommand command) {
        if (hasNoRight(rightsService.canAdmin(currentSite))) return;

        Site site = currentSite
        
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
        if (hasNoRight(rightsService.canAdmin(currentSite))) return;
        // TODO: move to services
        if (currentSite instanceof Profile) {
            Account account = currentAccount
            account.mainProfile = currentSite
            accountRepository.save(account)
        }
        redirect uri:  siteLinkService.getUrl(currentSite, [action: "preferences"])
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