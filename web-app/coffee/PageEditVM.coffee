exports = this
$ = exports.jQuery
$ ->
  class exports.PageEditVM
    constructor: ->
      @_action = null

      @inners = ko.observableArray([])

      @_title = ko.observable()

      @title = ko.dependentObservable
        read: =>
          if @inners().length == 1 then @inners()[0].title() else @_title()
        write: (v) =>
          if @inners().length == 1 then @inners()[0].title(v) else @_title(v)

      @id = ko.observable()

    addUnit: (unitJson)=>
      type = unitJson.type
      @inners.push new UnitEditImage(this, unitJson) if type is "Image"
      @inners.push new UnitEditText(this, unitJson) if type is "Text"

    addTextUnit: =>
      @addUnit
        type: "Text"
        id: null
        text: ""
        title: null

    unitTmpl: (unit) ->
      if unit.tmplName and unit.tmplName() then unit.tmplName() else "edit#{unit.type}"

    toJSON: ->
      ko.mapping.toJSON this,
        ignore: ["_title", "_parent", "_action", "tmplName", "toJSON"]

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
              unitAdder.animate {height: 100}, 400, 'linear'

        success: (data, textStatus, jqXHR) ->
          exports.serviceReact data, "#alerts", (mdl) -> console.log mdl

        error: (data, textStatus, jqXHR)->
          alert "Error"
    update: (element, valueAccessor, allBindingsAccessor, viewModel) ->
      console.log "updated"