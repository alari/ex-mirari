package mirari.site

import grails.plugins.springsecurity.Secured
import mirari.UtilController
import mirari.ko.PageViewModel
import mirari.model.Page
import mirari.model.disqus.Comment
import mirari.model.disqus.Reply
import mirari.util.ServiceResponse
import org.springframework.beans.factory.annotation.Autowired
import mirari.repo.*

class SitePageController extends UtilController {

    @Autowired UnitRepo unitRepo
    def unitActService
    def rightsService
    def commentActService
    def pageEditActService
    PageRepo pageRepo
    TagRepo tagRepo
    CommentRepo commentRepo
    ReplyRepo replyRepo

    private Page getCurrentPage() {
        pageRepo.getByName(_site, params.pageName)
    }

    def index() {
        Page page = currentPage
        if (isNotFound(page)) return;
        if (hasNoRight(rightsService.canView(page))) return;
        [page: page]
    }

    def commentsVM() {
        Page page = currentPage
        if (isNotFound(page)) return;
        if (hasNoRight(rightsService.canView(page))) return;

        renderJson commentActService.getPageCommentsVM(page)
    }

    @Secured("ROLE_USER")
    def postComment(PostCommentCommand command) {
        Page page = currentPage
        if (isNotFound(page)) return;
        if (hasNoRight(rightsService.canComment(page))) return;

        renderJson commentActService.postComment(page, command, _profile)
    }

    @Secured("ROLE_USER")
    def postReply(PostReplyCommand command) {
        Page page = currentPage
        if (isNotFound(page)) return;
        if (hasNoRight(rightsService.canComment(page))) return;

        renderJson commentActService.postReply(page, command, _profile)
    }

    @Secured("ROLE_USER")
    def removeReply(String replyId) {
        Reply reply = replyRepo.getById(replyId)
        if (isNotFound(reply)) return;
        if (hasNoRight(rightsService.canRemove(reply))) return;

        renderJson commentActService.remove(reply)
    }

    @Secured("ROLE_USER")
    def removeComment(String commentId) {
        Comment comment = commentRepo.getById(commentId)
        if (isNotFound(comment)) return;
        if (hasNoRight(rightsService.canRemove(comment))) return;

        renderJson commentActService.remove(comment)
    }

    @Secured("ROLE_USER")
    def edit() {
        Page page = currentPage
        if (isNotFound(page)) return;
        if (hasNoRight(rightsService.canEdit(page))) return;

        [page: page, tags: tagRepo.listBySite(_profile)]
    }

    @Secured("ROLE_USER")
    def save(EditPageCommand command) {
        Page page = currentPage
        if (isNotFound(page)) return;
        if (hasNoRight(rightsService.canEdit(page))) return;

        // TODO: move to services!
        PageViewModel vm = PageViewModel.forString(command.ko)
        page.viewModel = vm
        pageRepo.save(page)

        renderJson(new ServiceResponse().redirect(page.url))
    }

    @Secured("ROLE_USER")
    def saveAndContinue(EditPageCommand command) {
        Page page = currentPage
        if (isNotFound(page)) return;
        if (hasNoRight(rightsService.canEdit(page))) return;

        renderJson pageEditActService.saveAndContinue(page, command)
    }

    @Secured("ROLE_USER")
    def viewModel() {
        Page page = currentPage
        if (isNotFound(page)) return;
        if (hasNoRight(rightsService.canView(page))) return;

        renderJson pageEditActService.getViewModel(currentPage)
    }

    @Secured("ROLE_USER")
    def setDraft() {
        Page page = currentPage
        if (isNotFound(page)) return;
        if (hasNoRight(rightsService.canEdit(page))) return;

        redirect pageEditActService.setDraft(page, params.boolean("draft")).redirect
    }

    @Secured("ROLE_USER")
    def delete() {
        Page page = currentPage
        if (isNotFound(page)) return;
        if (hasNoRight(rightsService.canEdit(page))) return;

        redirect pageEditActService.delete(page).redirect
    }
}

class EditPageCommand {
    String title
    String ko
    boolean draft

    static constraints = {
        ko nullable: false, blank: false
    }
}

class PostCommentCommand {
    String title
    String text

    static constraints = {
        text nullable: false, blank: false
    }
}

class PostReplyCommand {
    String commentId
    String text

    static constraints = {
        text nullable: false, blank: false
    }
}