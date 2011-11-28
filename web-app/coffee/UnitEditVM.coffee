exports = this
$ = exports.jQuery
$ ->
  class exports.UnitEdit
    constructor: (@_parent, json)->
      @title = ko.observable(json.title)
      @id = json.id
      @type = json.type
      @params = json.params

  class exports.UnitEditImage extends UnitEdit
    tmplName: =>
      "editImage_tiny" if @_parent.units().length > 1

  class exports.UnitEditImageColl extends UnitEdit

  class exports.UnitEditText extends UnitEdit
    constructor: (@_parent, json)->
      super(@_parent, json)
      @text = ko.observable(json.text)