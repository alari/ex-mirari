exports = this
class exports.AvatarVM
  constructor: ->
    @srcLarge = ko.observable ""
    @srcFeed = ko.observable ""
    @srcTiny = ko.observable ""
    @name = ko.observable ""
    @basic = ko.observable true

ko.bindingHandlers.avatarUpload =
  init: (element, valueAccessor, allBindingsAccessor, viewModel) ->
    box = $(element)
    progressbar = $(".ui-progressbar", box).fadeOut()

    box.find("form").fileupload
        dataType: "json"
        dropZone: box

        add: (e, data) =>
          data.submit()

        send: (e, data) =>
          progressbar.progressbar({value: 0}).fadeIn()
          true

        progress: (e, data) =>
          progressbar.progressbar({value: parseInt(data.loaded/data.total * 100, 10)})

        stop: (e, data) =>
          progressbar.fadeOut()

        done: (e, data) =>
          serviceReact data.result, (mdl) =>
            console.log mdl

      success: (data, textStatus, jqXHR) ->
        serviceReact data, (mdl) -> console.log mdl

      error: (data, textStatus, jqXHR)->
        alert "Error"