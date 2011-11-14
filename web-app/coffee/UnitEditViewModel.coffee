exports = this
$ = exports.jQuery
$ ->
  class exports.UnitEditViewModel
    contents: ko.observableArray([])

    addUnit: (unitJsonObject)=>
      type = unitJsonObject.type
      @contents.push new UnitEditImage(unitJsonObject) if type is "Image"
      console.log @contents()

    unitTmpl: (unit) ->
      unit.tmplName

  class UnitEdit
    constructor: (obj)->
      @obj = obj
      @title = ko.observable(@obj.title)

  class UnitEditImage extends UnitEdit

    tmplName: "unitEditImage"