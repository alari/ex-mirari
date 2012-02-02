package mirari.site

import grails.plugins.springsecurity.Secured
import mirari.UtilController
import mirari.ko.CommentViewModel
import mirari.ko.PageViewModel
import mirari.ko.ReplyViewModel
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
        // TODO: we may collect replies and sort theirs comments to avoid some queries
        List<CommentViewModel> comments = []
        for (Comment c: commentRepo.listByPage(page)) {
            CommentViewModel cvm = c.viewModel
            List<ReplyViewModel> replies = []
            for (Reply r: replyRepo.listByComment(c)) {
                replies.add(r.viewModel)
            }
            cvm.put("replies", replies)
            comments.add(cvm)
        }
        renderJson(new ServiceResponse().model(comments: comments))
    }

    @Secured("ROLE_USER")
    def postComment(PostCommentCommand command) {
        Page page = currentPage
        if (isNotFound(page)) return;
        if (hasNoRight(rightsService.canComment(page))) return;

        ServiceResponse resp = new ServiceResponse()
        if (command.hasErrors()) {
            resp.error(command.errors.toString())
        } else {

            Comment comment = new Comment(
                    title: command.title,
                    text: command.text,
                    owner: _profile,
                    page: page,
            )
            commentRepo.save(comment)

            if (comment.isPersisted()) {
                resp.model(comment.viewModel)
            } else {
                resp.error("not saved")
            }

        }

        renderJson resp
    }

    @Secured("ROLE_USER")
    def postReply(PostReplyCommand command) {
        Page page = currentPage
        if (isNotFound(page)) return;
        if (hasNoRight(rightsService.canComment(page))) return;

        ServiceResponse resp = new ServiceResponse()
        if (command.hasErrors()) {
            resp.error(command.errors.toString())
        } else {
            Comment comment = commentRepo.getById(command.commentId)
            if (!comment || comment.page != page) {
                resp.error "Comment not found"
            } else {
                Reply reply = new Reply(
                        comment: comment,
                        text: command.text,
                        owner: _profile,
                        page: page,
                )
                replyRepo.save(reply)

                if (reply.isPersisted()) {
                    resp.model(reply.viewModel)
                } else {
                    resp.error("not saved")
                }
            }
        }

        renderJson resp
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

        PageViewModel vm = PageViewModel.forString(command.ko)
        page.viewModel = vm
        pageRepo.save(page)

        infoCode = "Сохранили: ".concat(new Date().toString())

        renderJson(new ServiceResponse().model(page.viewModel))
    }

    @Secured("ROLE_USER")
    def viewModel() {
        Page page = currentPage
        if (isNotFound(page)) return;
        if (hasNoRight(rightsService.canView(page))) return;

        ServiceResponse resp = new ServiceResponse()

        resp.model = currentPage.viewModel

        renderJson resp
    }

    @Secured("ROLE_USER")
    def setDraft() {
        Page page = currentPage
        if (isNotFound(page)) return;
        if (hasNoRight(rightsService.canEdit(page))) return;

        page.head.draft = params.boolean("draft")
        pageRepo.save(page)
        redirect url: page.url
    }

    @Secured("ROLE_USER")
    def delete() {
        Page page = currentPage
        if (isNotFound(page)) return;
        if (hasNoRight(rightsService.canEdit(page))) return;

        pageRepo.delete(page)
        successCode = "Deleted OK"
        redirect url: _site.url
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