exports = this

class exports.CommentVM
  constructor: ->
    @id = null
    @text = ko.observable("")
    @html = ""
    @owner = null
    @title = ""
    @dateCreated = null

  fromJson: (json) =>
    @id = json.id
    @text(json.text)
    @html = json.html
    @owner = json.owner
    @title = json.title
    @dateCreated = json.dateCreated

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
      cm = new CommentVM()
      cm.fromJson(c)
      @comments.push cm

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
          console.log mdl
          c = new CommentVM()
          c.fromJson mdl
          @comments.push c
      error: (data, textStatus, jqXHR)->
        alert "Error"
        