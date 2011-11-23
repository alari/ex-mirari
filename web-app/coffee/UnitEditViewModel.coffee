exports = this
$ = exports.jQuery
$ ->
  class exports.UnitEditViewModel
    constructor: ->
      @_action = null

      @contents = ko.observableArray([])

      @_title = ko.observable()

      @title = ko.dependentObservable
        read: =>
          if @contents().length == 1 then @contents()[0].title() else @_title()
        write: (v) =>
          if @contents().length == 1 then @contents()[0].title(v) else @_title(v)

      @id = ko.dependentObservable => @contents()[0].id if @contents().length == 1

    addUnit: (unitJsonObject)=>
      type = unitJsonObject.type
      @contents.push new UnitEditImage(this, unitJsonObject) if type is "Image"
      @contents.push new UnitEditText(this, unitJsonObject) if type is "Text"

    addTextUnit: =>
      @addUnit
        type: "Text"
        id: null
        container: @id
        params:
          text: ko.observable()
        title: null

    unitTmpl: (unit) ->
      unit.tmplName()

    toJSON: ->
      ko.mapping.toJSON this,
        ignore: ["_title", "_parent", "_action", "tmplName", "toJSON"]

    submitDraft: =>
      @submit true

    submit: (draft)=>
      $.ajax @_action,
        type: "post"
        dataType: "json"
        data:
          draft: if draft then true else false
          ko: @toJSON()
        success: (data, textStatus, jqXHR) ->
          exports.serviceReact data, "#alerts", (mdl) -> console.log mdl
        error: (data, textStatus, jqXHR)->
          alert "Error"

  class UnitEdit
    constructor: (@_parent, json)->
      @title = ko.observable(json.title)
      @id = json.id
      @container = json.container
      @type = json.type
      @params = json.params

  class UnitEditImage extends UnitEdit

    tmplName: =>
      if @_parent.contents().length > 1 then "unitTinyImageEdit" else "unitEditImage"

  class UnitEditImageCollection extends UnitEdit

    tmplName: => "unitEditImageCollection"

  class UnitEditText extends UnitEdit

    tmplName: => "unitEditText"