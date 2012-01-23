$ ->
  ko.bindingHandlers.autoResize =
    init: (element, valueAccessor, allBindingsAccessor, viewModel) ->
      $(element).autoResize
        maxHeight: 10000
        minHeight: 100
        extraSpace: 20

      $(element).trigger "change.autoResize"
