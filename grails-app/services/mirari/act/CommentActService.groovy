package mirari.act

import mirari.model.Page
import mirari.model.Site
import mirari.model.disqus.Comment
import mirari.model.disqus.Reply
import mirari.repo.CommentRepo
import mirari.repo.ReplyRepo
import mirari.page.PostCommentCommand
import mirari.page.PostReplyCommand
import mirari.util.ServiceResponse
import mirari.vm.CommentVM

class CommentActService {

    static transactional = false

    CommentRepo commentRepo
    ReplyRepo replyRepo

    ServiceResponse getPageCommentsVM(Page page) {
        // TODO: we may collect replies and sort theirs comments to avoid some queries
        List<CommentVM> commentVMs = []

        for (Comment c: commentRepo.listByPage(page)) {
            commentVMs.add CommentVM.build(c, replyRepo.listByComment(c))
        }

        new ServiceResponse().model(comments: commentVMs)
    }

    ServiceResponse postComment(Page page, PostCommentCommand command, Site owner) {
        ServiceResponse resp = new ServiceResponse()
        if (command.hasErrors()) {
            resp.error(command.errors.toString())
        } else {

            Comment comment = new Comment(
                    title: command.title,
                    text: command.text,
                    owner: owner,
                    page: page,
            )
            commentRepo.save(comment)

            if (comment.isPersisted()) {
                resp.model(comment: comment.viewModel)
            } else {
                resp.error("not saved")
            }
        }
        resp
    }

    ServiceResponse postReply(Page page, PostReplyCommand command, Site owner) {
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
                        owner: owner,
                        page: page,
                )
                replyRepo.save(reply)

                if (reply.isPersisted()) {
                    resp.model(reply: reply.viewModel)
                } else {
                    resp.error("not saved")
                }
            }
        }
        resp
    }

    ServiceResponse remove(Comment comment) {
        commentRepo.delete(comment)
        new ServiceResponse().success("deleted")
    }

    ServiceResponse remove(Reply reply) {
        replyRepo.delete(reply)
        new ServiceResponse().success("deleted")
    }
}
