exports = this
$ = exports.jQuery
$ =>

  $("*[data-avatar]").each ->
    $(this).find(".avatar-progressbar").hide()

    $(this).fileupload
      url: $(this).data("avatar")
      dataType: "json"
      paramName: "avatar"
      dropZone: $(this)

      add: (e, data) -> data.submit()

      done: (e, data) ->
        $("img", this).attr("src", data.result.thumbnail + "?" + new Date().getTime() + new Date().getUTCMilliseconds())

      fail: (e, data) -> alert data.result

      progress: (e, data) ->
        $(this).find(".ui-progressbar").progressbar('value', parseInt(data.loaded/data.total * 100, 10))

      start: -> $(this).find('.ui-progressbar').progressbar('value', 0).fadeIn()

      stop: ->
        $(this).find('.ui-progressbar').fadeOut()
        $(this).removeClass("avatar-dragover")

      dragover: -> $(this).addClass "avatar-dragover"

      drop: -> $(this).removeClass "avatar-dragover"

    $(this).bind "mouseover", -> $(this).removeClass "avatar-dragover"

    $(this).bind "mouseout", -> $(this).removeClass "avatar-dragover"