exports = this
$ = exports.jQuery
serviceReact = exports.serviceReact
$ ->

  class UnitEditContext
    constructor: (unitEnvelop)->
      @envelop = $(unitEnvelop)
      @action = @envelop.data("unit-action")
      @elems =
        title: $(".unit-title", @envelop)
        buttonPub: $(".unit-pub", @envelop)
        buttonDraft: $(".unit-draft", @envelop)
        unitAdder: $(".unit-adder", @envelop)
        content: $(".unit-content", @envelop)

      $(el).data("unit-context", this) for el in @elems

      @data=
        unitId: null
        title: @elems.title.value

      @elems.title.change @titleChange.bind(this)

      @elems.buttonPub.click @submitPub.bind(this)
      @elems.buttonDraft.click @submitDraft.bind(this)

      @buildUnitAdder()

    buildUnitAdder: ->
      @elems.unitAdder.find("form").fileupload
          dataType: "json"
          dropZone: @elems.unitAdder.find("unit-adder-drop")
          sequentialUploads: yes

          add: (e, data) => data.container = @data.unitId; data.submit()

          send: (e, data) => false if data.files.length > 1

          done: (e, data) =>
            serviceReact data.result, "#alerts", (mdl) =>
              console.log mdl
              @data.unitId = mdl.id
              @elems.content.append "<div data-unit-id='#{mdl.id}'>
              <img src='#{mdl.srcPage}'/><br/>
              <img src='#{mdl.srcFeed}'/><br/>
              <img src='#{mdl.srcTiny}'/><br/>
              <a target='_blank' href='#{mdl.srcMax}'>link to max</a></div>"

              @elems.unitAdder.animate {height: 100}, 400, 'linear'
              @elems.unitAdder.find("form").fileupload "destroy"

    titleChange: (eventObject) =>
      @data.title = eventObject.currentTarget.value

    submitPub: ->
      @data.draft = false;
      @submit();

    submitDraft: ->
      @data.draft = true;
      @submit();

    submit: ->
      $.ajax @action,
        type: "post"
        dataType: "json"
        data: @data

        success: (data, textStatus, jqXHR) ->
          serviceReact data, "#alerts", (mdl) -> console.log mdl

        error: -> alert "Error"

  new UnitEditContext "#unit"