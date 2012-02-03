
  $(function() {
    return ko.bindingHandlers.carousel = {
      init: function(element, valueAccessor, allBindingsAccessor, viewModel) {
        var params, _ref;
        params = valueAccessor();
        if ((_ref = params.interval) == null) params.interval = 5000;
        return $(element).carousel(params);
      }
    };
  });
