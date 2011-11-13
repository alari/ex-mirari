(function() {
  var $, exports;
  var _this = this;

  exports = this;

  $ = exports.jQuery;

  $(function() {
    return $("*[data-avatar]").each(function() {
      var _this = this;
      $(this).find(".avatar-progressbar").hide();
      $(this).fileupload({
        url: $(this).data("avatar"),
        dataType: "json",
        paramName: "avatar",
        dropZone: $(this),
        add: function(e, data) {
          return data.submit();
        },
        done: function(e, data) {
          return $("img", this).attr("src", data.result.thumbnail + "?" + new Date().getTime() + new Date().getUTCMilliseconds());
        },
        fail: function(e, data) {
          console.log(data.result);
          return alert(data.result);
        },
        progress: function(e, data) {
          return $(_this).find(".ui-progressbar").progressbar('value', parseInt(data.loaded / data.total * 100, 10));
        },
        start: function() {
          return $(this).find('.ui-progressbar').progressbar({
            value: 0
          }).fadeIn();
        },
        stop: function() {
          $(this).find('.ui-progressbar').fadeOut();
          return $(this).removeClass("avatar-dragover");
        },
        dragover: function() {
          return $(this).addClass("avatar-dragover");
        },
        drop: function() {
          return $(this).removeClass("avatar-dragover");
        }
      });
      $(this).bind("mouseover", function() {
        return $(this).removeClass("avatar-dragover");
      });
      return $(this).bind("mouseout", function() {
        return $(this).removeClass("avatar-dragover");
      });
    });
  });

}).call(this);
