(function() {
  var $, exports, serviceReact;

  exports = this;

  $ = exports.jQuery;

  serviceReact = exports.serviceReact;

  $(function() {
    return exports.UnitEditContext = (function() {

      function UnitEditContext(unitEnvelop) {
        console.log("Building for " + unitEnvelop);
        this.envelop = $(unitEnvelop);
        this.viewModel = new UnitEditViewModel();
      }

      UnitEditContext.prototype.buildUnitAdder = function() {
        var _this = this;
        this.unitAdder = $(".unit-adder", this.envelop);
        this.progressbar = $(".ui-progressbar", this.envelop).fadeOut();
        this.unitAdder.find("form").fileupload({
          dataType: "json",
          dropZone: this.unitAdder.find("unit-adder-drop"),
          sequentialUploads: true,
          add: function(e, data) {
            return data.submit();
          },
          send: function(e, data) {
            _this.progressbar.progressbar({
              value: 0
            }).fadeIn();
            if (data.files.length > 1) return false;
            return true;
          },
          progress: function(e, data) {
            return _this.progressbar.progressbar({
              value: parseInt(data.loaded / data.total * 100, 10)
            });
          },
          stop: function(e, data) {
            return _this.progressbar.fadeOut();
          },
          done: function(e, data) {
            return exports.serviceReact(data.result, "#alerts", function(mdl) {
              console.log(mdl);
              _this.viewModel.addUnit(mdl);
              return _this.unitAdder.animate({
                height: 100
              }, 400, 'linear');
            });
          }
        });
        return {
          success: function(data, textStatus, jqXHR) {
            return exports.serviceReact(data, "#alerts", function(mdl) {
              return console.log(mdl);
            });
          },
          error: function(data, textStatus, jqXHR) {
            return alert("Error");
          }
        };
      };

      return UnitEditContext;

    })();
  });

}).call(this);
