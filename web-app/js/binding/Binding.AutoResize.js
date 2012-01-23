
  $(function() {
    return ko.bindingHandlers.autoResize = {
      init: function(element, valueAccessor, allBindingsAccessor, viewModel) {
        $(element).autoResize({
          maxHeight: 10000,
          minHeight: 100,
          extraSpace: 20
        });
        return $(element).trigger("change.autoResize");
      }
    };
  });
