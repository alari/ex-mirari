
  ko.bindingHandlers.clickScroll = {
    update: function(element, valueAccessor, allBindingsAccessor, viewModel) {
      var target;
      target = valueAccessor();
      if (!target) target = $(element).attr('href');
      return $(element).click(function() {
        $('html, body').animate({
          scrollTop: $(target).offset().top
        }, 500);
        return false;
      });
    }
  };
