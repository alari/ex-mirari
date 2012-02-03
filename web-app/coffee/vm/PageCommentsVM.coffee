exports = this
class exports.PageCommentsVM
  constructor: (@pageUrl, @canPostComment)->
    jsonGetReact @url("commentsVM"), (mdl) =>
      @fromJson(mdl)

  newTitle: ko.observable ""
  newText: ko.observable ""
  comments: ko.observableArray []

  fromJson: (json)->
    @comments.push new CommentVM(this).fromJson(c) for c in json.comments if json.comments?.length
    this

  url: (action)->
    @pageUrl + "/" + action

  clear: =>
    @newTitle ""
    @newText ""

  postComment: ->
    return false if not @newText().length

    jsonPostReact @url("postComment"), {title: @newTitle, text: @newText}, (mdl) =>
      @clear()
      @comments.push new CommentVM().fromJson(mdl)