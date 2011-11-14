exports = this
$ = exports.jQuery
serviceReact = exports.serviceReact
$ ->

  class exports.UnitEditContext
    constructor: (unitEnvelop)->
      console.log "Building for #{unitEnvelop}"
      @envelop = $(unitEnvelop)
      @action = @envelop.data("unit-action")
      @elems =
        title: $(".unit-title", @envelop)
        buttonPub: $(".unit-pub", @envelop)
        buttonDraft: $(".unit-draft", @envelop)
        unitAdder: $(".unit-adder", @envelop)
        content: $(".unit-content", @envelop)
        progressbar: $(".ui-progressbar", @envelop).fadeOut()

      $(el).data("unit-context", this) for el in @elems

      @data=
        unitId: null
        title: @elems.title.value

      @elems.title.change @titleChange

      @elems.buttonPub.click @submitPub
      @elems.buttonDraft.click @submitDraft

      @buildUnitAdder()

    buildUnitAdder: ->
      @elems.unitAdder.find("form").fileupload
          dataType: "json"
          dropZone: @elems.unitAdder.find("unit-adder-drop")
          sequentialUploads: yes

          add: (e, data) => data.container = @data.unitId; data.submit()

          send: (e, data) =>
            @elems.progressbar.progressbar({value: 0}).fadeIn()
            false if data.files.length > 1

          progress: (e, data) =>
            @elems.progressbar.progressbar({value: parseInt(data.loaded/data.total * 100, 10)})

          stop: (e, data) =>
            @elems.progressbar.fadeOut()

          done: (e, data) =>
            exports.serviceReact data.result, "#alerts", (mdl) =>
              console.log mdl
              unitEditViewModel.addUnit mdl

              @data.unitId = mdl.id

              @elems.unitAdder.animate {height: 100}, 400, 'linear'
              #@elems.unitAdder.find("form").fileupload "destroy"

    titleChange: (eventObject) =>
      @data.title = eventObject.currentTarget.value


    submitPub: =>
      @data.draft = false;
      @submit();

    submitDraft: =>
      @data.draft = true;
      @submit();

    submit: =>
      @data.ko = ko.mapping.toJSON exports.unitEditViewModel
      console.log "sending:"
      console.log @data
      $.ajax @action,
        type: "post"
        dataType: "json"
        data: @data

        success: (data, textStatus, jqXHR) ->
          console.log "success:"
          console.log data
          exports.serviceReact data, "#alerts", (mdl) -> console.log mdl

        error: (data, textStatus, jqXHR)->
          console.log data
          alert "Error"