  exports = this
  $ = exports.jQuery

  addUnit = (container, unitJson)->
    type = unitJson.type
    unit = new UnitEditImage(container, unitJson) if type is "Image"
    unit = new UnitEditText(container, unitJson) if type is "Text"
    unit = new UnitEditAudio(container, unitJson) if type is "Audio"
    if unitJson.inners and unitJson.inners.length
      addUnit(unit, u) for u in unitJson.inners
    container.inners.push unit

  class exports.PageEditVM
    constructor: ->
      @_action = null

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
        types = []
        #@inners().each (u)->
        #  types.push u.type if u.type not in types
        return "ImageColl" if types.length is 1 and types[0] is "Image"
        return "Page"

      @innersCount = ko.dependentObservable =>
        (u for u in @.inners() when not u._destroy).length

    addUnit: (unitJson)=>
      addUnit(this, unitJson)

    addTextUnit: =>
      @addUnit
        type: "Text"
        id: null
        text: ""
        title: null

    unitTmpl: (unit) ->
      if unit.tmplName and unit.tmplName() then unit.tmplName() else "edit#{unit.type}"
    envelopTmplName: =>
      if unit.envelopTmplName and unit.envelopTmplName() then unit.envelopTmplName() else "unitEdit"

    toJSON: ->
      ko.mapping.toJSON this,
        ignore: ["_title", "_parent", "_action", "tmplName", "toJSON"]

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
          exports.serviceReact data, "#alerts", (mdl) -> console.log mdl
        error: (data, textStatus, jqXHR)->
          alert "Error"

  ko = exports.ko
  ko.bindingHandlers.pageFileUpload =
    init: (element, valueAccessor, allBindingsAccessor, viewModel) ->
      unitAdder = $(element)
      progressbar = $(".ui-progressbar", unitAdder).fadeOut()

      unitAdder.find("form").fileupload
          dataType: "json"
          dropZone: unitAdder
          sequentialUploads: yes

          add: (e, data) =>
            data.submit()

          send: (e, data) =>
            progressbar.progressbar({value: 0}).fadeIn()
            return false if data.files.length > 1
            true

          progress: (e, data) =>
            progressbar.progressbar({value: parseInt(data.loaded/data.total * 100, 10)})

          stop: (e, data) =>
            progressbar.fadeOut()

          done: (e, data) =>
            exports.serviceReact data.result, "#alerts", (mdl) =>
              console.log mdl
              viewModel.addUnit mdl

        success: (data, textStatus, jqXHR) ->
          exports.serviceReact data, "#alerts", (mdl) -> console.log mdl

        error: (data, textStatus, jqXHR)->
          alert "Error"
    update: (element, valueAccessor, allBindingsAccessor, viewModel) ->
      console.log "updated"