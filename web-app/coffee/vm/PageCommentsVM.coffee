exports = this
class exports.PageCommentsVM
  constructor: (@pageUrl, ownerId, @_profileId, @canPostComment)->
    @isPageOwner = @_profileId == ownerId
    jsonGetReact @url("commentsVM"), (mdl) =>
      @fromJson(mdl)
    @newComment = new NewComment(this)

  comments: ko.observableArray []
  isPageOwner: false

  fromJson: (json)->
    @pushComment(c) for c in json.comments if json.comments?.length
    this

  pushComment: (json)->
    @comments.push new CommentVM(this).fromJson(json)

  url: (action)->
    @pageUrl + "/" + action



class NewComment
  constructor: (@vm)->
    @title = ko.observable ""
    @text = ko.observable ""

  clear: =>
    @title ""
    @text ""

  post: =>
    return false if not @text().length

    jsonPostReact @vm.url("postComment"), {title: @title(), text: @text()}, (mdl) =>
      @clear()
      @vm.pushComment(mdl.comment)


