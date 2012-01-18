  exports = this
  $ = exports.jQuery

  class exports.PageEditVM
    constructor: ->
      @_action = null

      @inners = ko.observableArray([])

      @tags = ko.observableArray([])

      @_title = ko.observable()

      @title = ko.computed
        read: =>
          if @inners().length == 1 then @inners()[0].title() else @_title()
        write: (v) =>
          if @inners().length == 1 then @inners()[0].title(v); @_title(v) else @_title(v)

      @id = ko.observable()

      @type = ko.observable("page")

      @innersCount = ko.computed =>
        (u for u in @inners() when not u._destroy).length

    addUnit: (unitJson)=>
      UnitUtils.addUnitJson this, unitJson

    addTag: (json)=>
      @tags.push new TagVM(this).fromJSON(json)

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

    addHtmlUnit: =>
      UnitUtils.addHtmlUnit(this)

    addExternalUnit: =>
      UnitUtils.addExternalUnit(this)


    unitTmpl: (unit) ->
      "edit_#{unit.type}"

    toJSON: ->
      ko.mapping.toJSON this,
        ignore: ["_title", "_parent", "_action", "toJSON"]

    fromJSON: (json)->
      @inners.removeAll()
      @tags.removeAll()

      @_title json.title
      @id json.id
      @type json.type
      @addUnit(u) for u in json.inners
      @addTag(t) for t in json.tags

    saveAndContinue: =>
      #_t = this
      $.ajax "saveAndContinue",
        type: "post"
        dataType: "json"
        data:
          ko: @toJSON()
        success: (data, textStatus, jqXHR) =>
          exports.serviceReact data, (mdl) =>
            #_t.fromJSON(mdl)
            console.log mdl
        error: (data, textStatus, jqXHR)->
          alert "Error"

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
          exports.serviceReact data, (mdl) -> console.log mdl
        error: (data, textStatus, jqXHR)->
          alert "Error"

    hideAllInners: =>
      UnitUtils.walk(node, (n)-> n.innersVisible(false) if n.innersCount() > 0) for node in @inners()

    showAllInners: =>
      UnitUtils.walk(node, (n)-> n.innersVisible(true) if n.innersCount() > 0) for node in @inners()

    hideAllContent: =>
      UnitUtils.walk(node, (n)-> n.contentVisible(false)) for node in @inners()

    showAllContent: =>
      UnitUtils.walk(node, (n)-> n.contentVisible(true)) for node in @inners()
