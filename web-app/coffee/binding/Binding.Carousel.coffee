  ko.bindingHandlers.carousel =
    init: (element, valueAccessor, allBindingsAccessor, viewModel) ->
      params = valueAccessor()
      params.interval ?= 5000
      $(element).carousel params