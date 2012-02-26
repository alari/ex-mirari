ko.bindingHandlers.fixFloat =
  init: (element, valueAccessor, allBindingsAccessor, viewModel) ->
    el = $(element)
    el.css "width", "inherit"
    wrapper = el.parent()
    offset = valueAccessor()
    offset = 0 if not offset

    o = wrapper
    o = o.parent() while not o.hasClass("row")

    msie6 = $.browser is 'msie' and $.browser.version < 7
    if(not msie6)
      wrapper.css "position", "relative"
      el.css "position", "absolute"
      el.css "top", 0

      top = el.offset().top - parseFloat(el.css('margin-top').replace(/auto/, 0))
      $(window).scroll (event)->
        y = $(this).scrollTop()
        if(y+offset >= top)
          if(y+el.height() > o.height())
            el.css "position", "absolute"
            el.css "top", o.height()-el.height()
          else
            el.css "position", "fixed"
            el.css "top", offset
        else
          el.css "position", "absolute"
          el.css "top", 0