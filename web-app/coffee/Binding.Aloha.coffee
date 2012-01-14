ko.bindingHandlers.aloha =
  init: (element, valueAccessor, allBindingsAccessor, viewModel) ->
    $(element).attr "contenteditable", true
    Aloha.ready -> Aloha.jQuery(element).aloha()

    $(element).focus ->
      $this = $(this)
      $this.data 'html-before', $this.html()
    $(element).bind 'blur keyup paste', ->
      $this = $(this)
      if $this.data('html-before') isnt $this.html()
        $this.data 'html-before', $this.html()
        # TODO: get from value accessor
        viewModel.params.text = $this.html()
        $this.trigger('change')