ko.bindingHandlers.pageFileUpload =
  init: (element, valueAccessor, allBindingsAccessor, viewModel) ->
    unitAdder = $(element)
    progressbar = $(".ui-progressbar", unitAdder.parent()).fadeOut()

    unitAdder.find("form").fileupload
        dataType: "json"
        dropZone: unitAdder
        sequentialUploads: yes
        multipart: true

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
          serviceReact data.result, (mdl) =>
            viewModel.addUnit mdl.unit

      success: (data, textStatus, jqXHR) ->
        serviceReact data, (mdl) -> console.log mdl

      error: (data, textStatus, jqXHR)->
        alert "Error"
  update: (element, valueAccessor, allBindingsAccessor, viewModel) ->
    console.log "updated"