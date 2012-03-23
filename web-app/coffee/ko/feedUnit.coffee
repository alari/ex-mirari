ko.bindingHandlers.feedUnit =
  init: (element, valueAccessor, allBindingsAccessor, viewModel) ->
    params = valueAccessor()
    feedVM = new FeedUnitVM(params.url, params.style, params.last)
    feedVM.loadJson(0)
    allBindingsAccessor().template.data = feedVM