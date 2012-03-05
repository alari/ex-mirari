ko.bindingHandlers.clickScroll =
  update: (element, valueAccessor, allBindingsAccessor, viewModel) ->
    target = valueAccessor()
    if(not target)
      target = $(element).attr 'href'
    $(element).click ->
      $('html, body').animate(
        scrollTop: $( target ).offset().top
      , 500);
      false
