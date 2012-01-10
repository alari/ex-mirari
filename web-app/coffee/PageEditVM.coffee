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
      @_undo = null

      @inners = ko.observableArray([])

      @_title = ko.observable()

      @title = ko.dependentObservable
        read: =>
          if @inners().length == 1 then @inners()[0].title() else @_title()
        write: (v) =>
          if @inners().length == 1 then @inners()[0].title(v); @_title(v) else @_title(v)

      @id = ko.observable()

      @type = ko.dependentObservable =>
        return @inners()[0].type if @inners().length == 1
        return "page"

      @innersCount = ko.dependentObservable =>
        (u for u in @inners() when not u._destroy).length

    addUnit: (unitJson)=>
      addUnit(this, unitJson)


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
        ignore: ["_title", "_parent", "_action", "_undo", "toJSON"]

    fromJSON: (json)->
      @_title json.title
      @id json.id
      #@type json.type
      @addUnit(u) for u in json.inners

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