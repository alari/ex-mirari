exports = this
class exports.PageCommentsVM
  constructor: (@pageUrl, ownerId, @_profileId, @canPostComment)->
    @isPageOwner = @_profileId == ownerId
    jsonGetReact @url("commentsVM"), (mdl) =>
      @fromJson(mdl)

  newCommentTitle: ko.observable ""
  newCommentText: ko.observable ""
  comments: ko.observableArray []
  isPageOwner: false

  fromJson: (json)->
    @comments.push new CommentVM(this).fromJson(c) for c in json.comments if json.comments?.length
    this

  url: (action)->
    @pageUrl + "/" + action

  clear: =>
    @newCommentTitle ""
    @newCommentText ""

  postComment: ->
    return false if not @newCommentText().length

    jsonPostReact @url("postComment"), {title: @newCommentTitle(), text: @newCommentText()}, (mdl) =>
      @clear()
      @comments.push new CommentVM(this).fromJson(mdl.comment)