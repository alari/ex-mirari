exports = this

class exports.CommentVM
  constructor: (@_parent)->
    @id = null
    @text = ko.observable("")
    @html = ""
    @owner = null
    @title = ""
    @dateCreated = null
    @replies = ko.observableArray([])
    @canPostReply = @_parent.canPostComment

    @newText = ko.observable ""

  clear: => @newText ""

  url: (action)=> @_parent.url(action)

  isCurrentProfileId: (id)->
    @_parent._profileId is id

  isCurrentPageOwner: ->
    @_parent.isPageOwner

  canRemove: ->
    @isCurrentProfileId(@owner.id) or @isCurrentPageOwner()

  remove: ->
    jsonPostReact @_parent.url("removeComment"), {commentId: @id}, (mdl)=>
      @_parent.comments.remove this

  fromJson: (json) =>
    @id = json.id
    @text(json.text)
    @html = json.html
    @owner = json.owner
    @title = json.title
    @dateCreated = json.dateCreated
    if json.replies?.length
      for r in json.replies
        @replies.push new ReplyVM(this).fromJson(r)
    this

  postReply: ->
    return null if not @newText()

    jsonPostReact @_parent.url("postReply"), {commentId: @id, text: @newText()}, (mdl) =>
      @clear()
      @replies.push new ReplyVM().fromJson(mdl)