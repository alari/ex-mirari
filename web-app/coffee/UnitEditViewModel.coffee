exports = this
$ = exports.jQuery
$ ->
  class exports.UnitEditViewModel
    constructor: ->
      @contents = ko.observableArray([])

      @_title = ko.observable()

      @title = ko.dependentObservable
        read: =>
          if @contents().length == 1 then @contents()[0].title() else @_title()
        write: (v) =>
          if @contents().length == 1 then @contents()[0].title(v) else @_title(v)

      @id = ko.dependentObservable => this.contents()[0].id if this.contents().length == 1

    addUnit: (unitJsonObject)=>
      type = unitJsonObject.type
      @contents.push new UnitEditImage(this, unitJsonObject) if type is "Image"

    unitTmpl: (unit) ->
      unit.tmplName

  class UnitEdit
    constructor: (@vm, json)->
      @title = ko.observable(json.title)
      @id = json.id
      @container = json.container
      @type = json.type
      @params = json.params

  class UnitEditImage extends UnitEdit

    tmplName: "unitEditImage"