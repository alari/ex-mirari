package mirari.act

import mirari.util.ServiceResponse
import mirari.model.Page
import mirari.ko.CommentViewModel
import mirari.model.disqus.Comment
import mirari.ko.ReplyViewModel
import mirari.model.disqus.Reply
import mirari.repo.CommentRepo
import mirari.repo.ReplyRepo
import mirari.site.PostCommentCommand
import mirari.model.Site
import mirari.site.PostReplyCommand

class CommentActService {

    static transactional = false
    
    CommentRepo commentRepo
    ReplyRepo replyRepo
    
    ServiceResponse getPageCommentsVM(Page page) {
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
        new ServiceResponse().model(comments: comments)
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
                resp.model(comment.viewModel)
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
                    resp.model(reply.viewModel)
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
