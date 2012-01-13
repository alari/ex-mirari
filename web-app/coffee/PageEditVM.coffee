  exports = this
  $ = exports.jQuery

  addUnit = (container, unitJson)->
    type = unitJson.type
    unit = new UnitVM(container, unitJson)
    if unitJson.inners and unitJson.inners.length
      addUnit(unit, u) for u in unitJson.inners
    container.inners.push unit

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

      @type = ko.computed =>
        return @inners()[0].type if @inners().length == 1
        return "page"

      @innersCount = ko.computed =>
        (u for u in @inners() when not u._destroy).length

    addUnit: (unitJson)=>
      addUnit(this, unitJson)

    addTag: (json)=>
      @tags.push new TagVM(this).fromJSON(json)

    addTagPrompt: =>
      @tags.push new TagVM(this, prompt("Tag display name?"))

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
        console.log "backspace"
        @tags.remove @tags()[@tags().length-1]
      if input.value and  event.which in stops
        @tags.push new TagVM(this, input.value)
        input.value = ""
      true

    addHtmlUnit: =>
      @addUnit
        type: "html"
        id: null
        text: ""
        title: null

    addExternalUnit: =>
      url = prompt("YouTube, Russia.Ru")
      return null if not url
      $.ajax "/p/addExternal",
        type: "post"
        dataType: "json"
        data:
          url: url
        success: (data, textStatus, jqXHR) =>
          exports.serviceReact data, (mdl) =>
            @addUnit mdl
        error: (data, textStatus, jqXHR)->
          alert "Error"


    unitTmpl: (unit) ->
      "edit_#{unit.type}"

    toJSON: ->
      ko.mapping.toJSON this,
        ignore: ["_title", "_parent", "_action", "toJSON"]

    fromJSON: (json)->
      @_title json.title
      @id json.id
      #@type json.type
      @addUnit(u) for u in json.inners
      @addTag(t) for t in json.tags

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