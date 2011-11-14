(function() {
  var $, exports, serviceReact;
  var __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; };

  exports = this;

  $ = exports.jQuery;

  serviceReact = exports.serviceReact;

  $(function() {
    return exports.UnitEditContext = (function() {

      function UnitEditContext(unitEnvelop) {
        this.submit = __bind(this.submit, this);
        this.submitDraft = __bind(this.submitDraft, this);
        this.submitPub = __bind(this.submitPub, this);
        this.titleChange = __bind(this.titleChange, this);
        var el, _i, _len, _ref;
        console.log("Building for " + unitEnvelop);
        this.envelop = $(unitEnvelop);
        this.action = this.envelop.data("unit-action");
        this.elems = {
          title: $(".unit-title", this.envelop),
          buttonPub: $(".unit-pub", this.envelop),
          buttonDraft: $(".unit-draft", this.envelop),
          unitAdder: $(".unit-adder", this.envelop),
          content: $(".unit-content", this.envelop),
          progressbar: $(".ui-progressbar", this.envelop).fadeOut()
        };
        _ref = this.elems;
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          el = _ref[_i];
          $(el).data("unit-context", this);
        }
        this.data = {
          unitId: null,
          title: this.elems.title.value
        };
        this.elems.title.change(this.titleChange);
        this.elems.buttonPub.click(this.submitPub);
        this.elems.buttonDraft.click(this.submitDraft);
        this.buildUnitAdder();
      }

      UnitEditContext.prototype.buildUnitAdder = function() {
        var _this = this;
        return this.elems.unitAdder.find("form").fileupload({
          dataType: "json",
          dropZone: this.elems.unitAdder.find("unit-adder-drop"),
          sequentialUploads: true,
          add: function(e, data) {
            data.container = _this.data.unitId;
            return data.submit();
          },
          send: function(e, data) {
            _this.elems.progressbar.progressbar({
              value: 0
            }).fadeIn();
            if (data.files.length > 1) return false;
          },
          progress: function(e, data) {
            return _this.elems.progressbar.progressbar({
              value: parseInt(data.loaded / data.total * 100, 10)
            });
          },
          stop: function(e, data) {
            return _this.elems.progressbar.fadeOut();
          },
          done: function(e, data) {
            return exports.serviceReact(data.result, "#alerts", function(mdl) {
              console.log(mdl);
              unitEditViewModel.addUnit(mdl);
              _this.data.unitId = mdl.id;
              return _this.elems.unitAdder.animate({
                height: 100
              }, 400, 'linear');
            });
          }
        });
      };

      UnitEditContext.prototype.titleChange = function(eventObject) {
        return this.data.title = eventObject.currentTarget.value;
      };

      UnitEditContext.prototype.submitPub = function() {
        this.data.draft = false;
        return this.submit();
      };

      UnitEditContext.prototype.submitDraft = function() {
        this.data.draft = true;
        return this.submit();
      };

      UnitEditContext.prototype.submit = function() {
        this.data.ko = ko.mapping.toJSON(exports.unitEditViewModel);
        console.log("sending:");
        console.log(this.data);
        return $.ajax(this.action, {
          type: "post",
          dataType: "json",
          data: this.data,
          success: function(data, textStatus, jqXHR) {
            console.log("success:");
            console.log(data);
            return exports.serviceReact(data, "#alerts", function(mdl) {
              return console.log(mdl);
            });
          },
          error: function(data, textStatus, jqXHR) {
            console.log(data);
            return alert("Error");
          }
        });
      };

      return UnitEditContext;

    })();
  });

}).call(this);
