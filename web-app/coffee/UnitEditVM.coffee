exports = this
$ = exports.jQuery
$ ->
  class exports.UnitEdit
    constructor: (@_parent, json)->
      @title = ko.observable(json.title)
      @id = json.id
      @type = json.type
      @params = json.params
      @inners = ko.observableArray([])

      @titleVisible = ko.dependentObservable =>
        @_titleVisible()

      @innersCount = ko.dependentObservable =>
        (u for u in @.inners() when not u._destroy).length

    remove: =>
      @_parent.inners.destroy this

    sortTo: (newParent, position)=>
      if (position >= 0)
        @_parent.inners.remove this
        @_parent = newParent
        @_parent.inners.splice position, 0, this

    envelopTmplName: =>
      "unitEdit"

    _titleVisible: =>
      true if @_parent.innersCount() > 1 or @_parent instanceof UnitEdit

  class exports.UnitEditImage extends UnitEdit
    tmplName: =>
      "editImage_tiny" if @_parent.inners().length > 1 and false
    _titleVisible: -> false

  class exports.UnitEditImageColl extends UnitEdit

  class exports.UnitEditHtml extends UnitEdit
    constructor: (@_parent, json)->
      super(@_parent, json)
      @text = json.text

  class exports.UnitEditAudio extends UnitEdit
    _titleVisible: -> false

  class exports.UnitEditYouTube extends UnitEdit
    _titleVisible: -> false

    constructor: (@_parent, json)->
      super(@_parent, json)
      @youtubeId = json.params.externalId

  class exports.UnitEditRussiaRu extends UnitEdit
    _titleVisible: -> false

    constructor: (@_parent, json)->
      super(@_parent, json)
      @videoId = json.params.externalId