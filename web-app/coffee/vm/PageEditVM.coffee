  exports = this
  $ = exports.jQuery

  class exports.PageEditVM
    constructor: ->
      @_action = null

      @inners = ko.observableArray([])

      @tags = ko.observableArray([])

      @title = ko.observable ""

      @id = ko.observable()

      @type = ko.observable("page")

      @draft = ko.observable(true)

      @innersCount = ko.computed =>
        (u for u in @inners() when not u._destroy).length

      @avatar = new AvatarVM()

    addUnit: (unitJson)=>
      UnitUtils.addUnitJson this, unitJson

    addTag: (json)=>
      @tags.push new TagVM(this).fromJson(json)

    addNewTag: (data, event)=>
      value = event.target?.value
      if(value)
        @tags.push new TagVM(this, value)
      event.target.value = ""

    tagInputKey: (data, event)=>
      keys =
        backspace: [8]
        enter:     [13]
        space:     [32]
        comma:     [44,188]
        tab:       [9]
      stops = [13, 9]
      input = event.target
      if(not input.value and event.which is 8)
        @tags.remove @tags()[@tags().length-1]
      if input.value and  event.which in stops
        @tags.push new TagVM(this, input.value)
        input.value = ""
      true

    addExternalUnit: =>
      UnitUtils.addExternalUnit(this)

    addTextUnit: =>
      UnitUtils.addTextUnit(this)

    addRenderInnersUnit: =>
      UnitUtils.addRenderInnersUnit(this)

    unitTmpl: (unit) ->
      "edit_#{unit.type}"

    toJSON: ->
      ko.mapping.toJSON this,
        ignore: ["_parent", "_action", "toJSON", "avatar", "innersCount", "innersVisible", "contentVisible"]

    fromJson: (json)->
      @inners.removeAll()
      @tags.removeAll()

      @title json.title
      @id json.id
      @type json.type
      @draft json.draft
      @addUnit(u) for u in json.inners
      @addTag(t) for t in json.tags

    saveAndContinue: =>
      return false if UnitUtils.isEmpty this
      _t = this
      jsonPostReact "saveAndContinue", {ko: @toJSON()}, (mdl) =>
            _t.fromJson(mdl.page)
            _t.id mdl.page.id

    submitDraft: =>
      @draft(true)
      @submit()

    submitPub: =>
      @draft(false)
      @submit()

    submit: =>
      return false if UnitUtils.isEmpty this
      jsonPostReact @_action, {draft: @draft(), ko: @toJSON()}, (mdl) ->
        console.log mdl

    hideAllInners: =>
      UnitUtils.walk(node, (n)-> n.innersVisible(false) if n.innersCount() > 0) for node in @inners()

    showAllInners: =>
      UnitUtils.walk(node, (n)-> n.innersVisible(true) if n.innersCount() > 0) for node in @inners()

    hideAllContent: =>
      UnitUtils.walk(node, (n)-> n.contentVisible(false)) for node in @inners()

    showAllContent: =>
      UnitUtils.walk(node, (n)-> n.contentVisible(true)) for node in @inners()
