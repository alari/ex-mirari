exports = this
$ = exports.jQuery
serviceReact = exports.serviceReact
$ ->

  class exports.UnitEditContext
    constructor: (unitEnvelop)->
      console.log "Building for #{unitEnvelop}"
      @envelop = $(unitEnvelop)

      @viewModel = new UnitEditViewModel()

    buildUnitAdder: ->
      @unitAdder = $(".unit-adder", @envelop)
      @progressbar = $(".ui-progressbar", @envelop).fadeOut()

      @unitAdder.find("form").fileupload
          dataType: "json"
          dropZone: @unitAdder.find("unit-adder-drop")
          sequentialUploads: yes

          add: (e, data) =>
            data.submit()

          send: (e, data) =>
            @progressbar.progressbar({value: 0}).fadeIn()
            return false if data.files.length > 1
            true

          progress: (e, data) =>
            @progressbar.progressbar({value: parseInt(data.loaded/data.total * 100, 10)})

          stop: (e, data) =>
            @progressbar.fadeOut()

          done: (e, data) =>
            exports.serviceReact data.result, "#alerts", (mdl) =>
              console.log mdl
              @viewModel.addUnit mdl
              @unitAdder.animate {height: 100}, 400, 'linear'

        success: (data, textStatus, jqXHR) ->
          exports.serviceReact data, "#alerts", (mdl) -> console.log mdl

        error: (data, textStatus, jqXHR)->
          alert "Error"