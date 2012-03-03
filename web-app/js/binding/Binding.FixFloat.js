
  ko.bindingHandlers.fixFloat = {
    init: function(element, valueAccessor, allBindingsAccessor, viewModel) {
      var el, msie6, o, offset, top, wrapper;
      el = $(element);
      el.css("width", "inherit");
      wrapper = el.parent();
      offset = valueAccessor();
      if (!offset) offset = 0;
      o = wrapper;
      while (!o.hasClass("row")) {
        o = o.parent();
      }
      msie6 = $.browser === 'msie' && $.browser.version < 7;
      if (!msie6) {
        wrapper.css("position", "relative");
        el.css("position", "absolute");
        el.css("top", 0);
        top = el.offset().top - parseFloat(el.css('margin-top').replace(/auto/, 0));
        return $(window).scroll(function(event) {
          var scrollY;
          scrollY = $(this).scrollTop();
          if (scrollY + offset >= top) {
            if (scrollY - offset + el.height() > o.height()) {
              el.css("position", "absolute");
              return el.css("top", o.height() - el.height() - parseFloat(el.css('margin-top').replace(/auto/, 0)));
            } else {
              el.css("position", "fixed");
              return el.css("top", offset);
            }
          } else {
            el.css("position", "absolute");
            return el.css("top", 0);
          }
        });
      }
    }
  };
