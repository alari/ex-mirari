
  ko.bindingHandlers.pageFileUpload = {
    init: function(element, valueAccessor, allBindingsAccessor, viewModel) {
      var progressbar, unitAdder,
        _this = this;
      unitAdder = $(element);
      progressbar = $(".ui-progressbar", unitAdder.parent()).fadeOut();
      unitAdder.find("form").fileupload({
        url: "/p/addFile",
        dataType: "json",
        dropZone: unitAdder,
        sequentialUploads: true,
        multipart: true,
        add: function(e, data) {
          return data.submit();
        },
        send: function(e, data) {
          progressbar.progressbar({
            value: 0
          }).fadeIn();
          if (data.files.length > 1) return false;
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
            return viewModel.innersAct.addUnit(mdl.unit);
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
