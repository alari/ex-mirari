exports = this
$ = exports.jQuery
$ ->
  class exports.UnitEditViewModel
    constructor: ->
      @_action = null

      @units = ko.observableArray([])

      @_title = ko.observable()

      @title = ko.dependentObservable
        read: =>
          if @units().length == 1 then @units()[0].title() else @_title()
        write: (v) =>
          if @units().length == 1 then @units()[0].title(v) else @_title(v)

      @id = ko.dependentObservable => @units()[0].id if @units().length == 1

    addUnit: (unitJson)=>
      type = unitJson.type
      @units.push new UnitEditImage(this, unitJson) if type is "Image"
      @units.push new UnitEditText(this, unitJson) if type is "Text"

    addTextUnit: =>
      @addUnit
        type: "Text"
        id: null
        text: ""
        title: null

    unitTmpl: (unit) ->
      if unit.tmplName and unit.tmplName() then unit.tmplName() else "edit#{unit.type}"

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
          draft: if draft is true then true else false
          ko: @toJSON()
        success: (data, textStatus, jqXHR) ->
          exports.serviceReact data, "#alerts", (mdl) -> console.log mdl
        error: (data, textStatus, jqXHR)->
          alert "Error"

  class UnitEdit
    constructor: (@_parent, json)->
      @title = ko.observable(json.title)
      @id = json.id
      @type = json.type
      @params = json.params

  class UnitEditImage extends UnitEdit
    tmplName: =>
      "editImage_tiny" if @_parent.units().length > 1

  class UnitEditImageColl extends UnitEdit

  class UnitEditText extends UnitEdit
    constructor: (@_parent, json)->
      super(@_parent, json)
      @text = ko.observable(json.text)