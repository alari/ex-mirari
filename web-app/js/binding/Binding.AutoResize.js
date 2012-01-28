
  $(function() {
    return ko.bindingHandlers.autoResize = {
      init: function(element, valueAccessor, allBindingsAccessor, viewModel) {
        return $(element).autoResize({
          maxHeight: 10000,
          minHeight: 100,
          extraSpace: 20
        });
      }
    };
  });
