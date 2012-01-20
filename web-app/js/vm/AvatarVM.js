(function() {
  var exports;

  exports = this;

  exports.AvatarVM = (function() {

    function AvatarVM() {
      this.srcLarge = ko.observable("");
      this.srcFeed = ko.observable("");
      this.srcTiny = ko.observable("");
      this.name = ko.observable("NoName");
      this.uploadAction = ko.observable("");
    }

    return AvatarVM;

  })();

  ko.bindingHandlers.avatarUpload = {
    init: function(element, valueAccessor, allBindingsAccessor, viewModel) {
      var box, progressbar,
        _this = this;
      box = $(element);
      progressbar = $(".ui-progressbar", box).fadeOut();
      box.find("form").fileupload({
        dataType: "json",
        dropZone: box,
        add: function(e, data) {
          return data.submit();
        },
        send: function(e, data) {
          progressbar.progressbar({
            value: 0
          }).fadeIn();
          return true;
        },
        progress: function(e, data) {
          return progressbar.progressbar({
            value: parseInt(data.loaded / data.total * 100, 10)
          });
        },
        stop: function(e, data) {
          return progressbar.fadeOut();
        },
        done: function(e, data) {
          return serviceReact(data.result, function(mdl) {
            return console.log(mdl);
          });
        }
      });
      return {
        success: function(data, textStatus, jqXHR) {
          return serviceReact(data, function(mdl) {
            return console.log(mdl);
          });
        },
        error: function(data, textStatus, jqXHR) {
          return alert("Error");
        }
      };
    }
  };

}).call(this);
