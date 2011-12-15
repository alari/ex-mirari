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

  class exports.UnitEditText extends UnitEdit
    constructor: (@_parent, json)->
      super(@_parent, json)
      @text = json.text

  class exports.UnitEditAudio extends UnitEdit
    _titleVisible: -> false

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

  ko.bindingHandlers.audio =
    init: (element, valueAccessor, allBindingsAccessor, viewModel) ->
      params = valueAccessor()
      $(element).mediaelementplayer(
        pluginPath: params
        #// width of audio player
        audioWidth: 400
        #// height of audio player
        audioHeight: 30
        #// initial volume when the player starts
        startVolume: 0.8
        #// useful for <audio> player loops
        loop: false
        #// enables Flash and Silverlight to resize to content size
        enableAutosize: true
        #// the order of controls you want on the control bar (and other plugins below)
        features: ['playpause','progress','current','duration','tracks','volume','fullscreen']

        #// automatically selects a <track> element
        startLanguage: ''
        #// a list of languages to auto-translate via Google
        translations: []
        #// a dropdownlist of automatic translations
        translationSelector: false
        #// key for tranlsations
        googleApiKey: ''
      )