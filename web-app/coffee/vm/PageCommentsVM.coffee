exports = this
class exports.PageCommentsVM
  constructor: (@pageUrl, @showAddForm)->
    @comments = ko.observableArray([])
    @newTitle = ko.observable("")
    @newText = ko.observable("")

    $.ajax @url("commentsVM"),
      type: "get",
      dataType: "json",
      success: (data, textStatus, jqXHR) =>
        exports.serviceReact data, (mdl) =>
          @fromJson(mdl)
      error: (data, textStatus, jqXHR)->
        alert "Error"

  fromJson: (json)->
    for c in json.comments
      @comments.push new CommentVM(this).fromJson(c)
    this

  url: (action)->
    @pageUrl + "/" + action

  clear: =>
    @newTitle ""
    @newText ""

  postComment: ->
    return false if not @newText().length

    $.ajax @url("postComment"),
      type: "post"
      dataType: "json"
      data:
        title: @newTitle(),
        text: @newText()
      success: (data, textStatus, jqXHR) =>
        exports.serviceReact data, (mdl) =>
          @clear()
          @comments.push new CommentVM().fromJson(mdl)
      error: (data, textStatus, jqXHR)->
        alert "Error"