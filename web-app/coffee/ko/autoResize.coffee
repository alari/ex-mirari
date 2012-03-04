$ ->
  ko.bindingHandlers.autoResize =
    init: (element, valueAccessor, allBindingsAccessor, viewModel) ->
      params = valueAccessor()
      params.maxHeight ?= 10000
      params.minHeight ?= 100
      params.extraSpace ?= 20
      $(element).autoResize params