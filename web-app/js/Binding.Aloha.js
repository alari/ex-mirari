
  ko.bindingHandlers.aloha = {
    init: function(element, valueAccessor, allBindingsAccessor, viewModel) {
      $(element).attr("contenteditable", true);
      Aloha.ready(function() {
        return Aloha.jQuery(element).aloha();
      });
      $(element).focus(function() {
        var $this;
        $this = $(this);
        return $this.data('html-before', $this.html());
      });
      return $(element).bind('blur keyup paste', function() {
        var $this;
        $this = $(this);
        if ($this.data('html-before') !== $this.html()) {
          $this.data('html-before', $this.html());
          viewModel.params.text = $this.html();
          return $this.trigger('change');
        }
      });
    }
  };
