exports = this
$ = exports.jQuery
$ ->
  class exports.UnitEdit
    constructor: (@_parent, json)->
      @title = ko.observable(json.title)
      @titleVisible = ko.dependentObservable => @_parent.inners().length > 1
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
      @text = json.text

  ko = exports.ko
  ko.bindingHandlers.aloha =
    init: (element, valueAccessor, allBindingsAccessor, viewModel) ->
      $(element).attr "contenteditable", true

      $(element).focus ->
        $this = $(this)
        $this.data 'html-before', $this.html()
      $(element).bind 'blur keyup paste', ->
        $this = $(this)
        if $this.data('html-before') isnt $this.html()
          $this.data 'html-before', $this.html()
          viewModel.text = $this.html()
          $this.trigger('change')