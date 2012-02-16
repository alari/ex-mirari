
  ko.bindingHandlers.avatarUpload = {
    init: function(element, valueAccessor, allBindingsAccessor, viewModel) {
      var box, progressbar,
        _this = this;
      box = $(element);
      progressbar = $(".ui-progressbar", box).fadeOut();
      return box.find("form").fileupload({
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
        },
        success: function(data, textStatus, jqXHR) {
          return serviceReact(data, function(mdl) {
            return console.log(mdl);
          });
        },
        error: function(data, textStatus, jqXHR) {
          return alert("Error");
        }
      });
    }
  };
