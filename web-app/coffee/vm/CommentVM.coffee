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

    $.ajax @_parent.url("postReply"),
      type: "post"
      dataType: "json"
      data:
        commentId: @id,
        text: @newText()
      success: (data, textStatus, jqXHR) =>
        exports.serviceReact data, (mdl) =>
          @clear()
          @replies.push new ReplyVM().fromJson(mdl)
      error: (data, textStatus, jqXHR)->
        alert "Error"