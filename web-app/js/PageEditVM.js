(function() {
  var $, addUnit, exports, ko,
    __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; };

  exports = this;

  $ = exports.jQuery;

  addUnit = function(container, unitJson) {
    var type, u, unit, _i, _len, _ref;
    type = unitJson.type;
    if (type === "Image") unit = new UnitEditImage(container, unitJson);
    if (type === "Text") unit = new UnitEditText(container, unitJson);
    if (type === "Audio") unit = new UnitEditAudio(container, unitJson);
    if (unitJson.inners && unitJson.inners.length) {
      _ref = unitJson.inners;
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        u = _ref[_i];
        addUnit(unit, u);
      }
    }
    return container.inners.push(unit);
  };

  exports.PageEditVM = (function() {

    function PageEditVM() {
      this.submit = __bind(this.submit, this);
      this.submitDraft = __bind(this.submitDraft, this);
      this.envelopTmplName = __bind(this.envelopTmplName, this);
      this.addTextUnit = __bind(this.addTextUnit, this);
      this.addUnit = __bind(this.addUnit, this);
      var _this = this;
      this._action = null;
      this._undo = null;
      this.inners = ko.observableArray([]);
      this._title = ko.observable();
      this.title = ko.dependentObservable({
        read: function() {
          if (_this.inners().length === 1) {
            return _this.inners()[0].title();
          } else {
            return _this._title();
          }
        },
        write: function(v) {
          if (_this.inners().length === 1) {
            _this.inners()[0].title(v);
            return _this._title(v);
          } else {
            return _this._title(v);
          }
        }
      });
      this.id = ko.observable();
      this.type = ko.dependentObservable(function() {
        var types;
        if (_this.inners().length === 1) return _this.inners()[0].type;
        types = [];
        if (types.length === 1 && types[0] === "Image") return "ImageColl";
        return "Page";
      });
      this.innersCount = ko.dependentObservable(function() {
        var u;
        return ((function() {
          var _i, _len, _ref, _results;
          _ref = this.inners();
          _results = [];
          for (_i = 0, _len = _ref.length; _i < _len; _i++) {
            u = _ref[_i];
            if (!u._destroy) _results.push(u);
          }
          return _results;
        }).call(_this)).length;
      });
    }

    PageEditVM.prototype.addUnit = function(unitJson) {
      return addUnit(this, unitJson);
    };

    PageEditVM.prototype.addTextUnit = function() {
      return this.addUnit({
        type: "Text",
        id: null,
        text: "",
        title: null
      });
    };

    PageEditVM.prototype.unitTmpl = function(unit) {
      if (unit.tmplName && unit.tmplName()) {
        return unit.tmplName();
      } else {
        return "edit" + unit.type;
      }
    };

    PageEditVM.prototype.envelopTmplName = function() {
      if (unit.envelopTmplName && unit.envelopTmplName()) {
        return unit.envelopTmplName();
      } else {
        return "unitEdit";
      }
    };

    PageEditVM.prototype.toJSON = function() {
      return ko.mapping.toJSON(this, {
        ignore: ["_title", "_parent", "_action", "_undo", "tmplName", "toJSON"]
      });
    };

    PageEditVM.prototype.fromJSON = function(json) {
      var u, _i, _len, _ref, _results;
      this._title(json.title);
      this.id(json.id);
      _ref = json.inners;
      _results = [];
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        u = _ref[_i];
        _results.push(this.addUnit(u));
      }
      return _results;
    };

    PageEditVM.prototype.submitDraft = function() {
      return this.submit(true);
    };

    PageEditVM.prototype.submit = function(draft) {
      return $.ajax(this._action, {
        type: "post",
        dataType: "json",
        data: {
          draft: draft === true ? true : false,
          ko: this.toJSON()
        },
        success: function(data, textStatus, jqXHR) {
          return exports.serviceReact(data, function(mdl) {
            return console.log(mdl);
          });
        },
        error: function(data, textStatus, jqXHR) {
          return alert("Error");
        }
      });
    };

    return PageEditVM;

  })();

  ko = exports.ko;

  ko.bindingHandlers.pageFileUpload = {
    init: function(element, valueAccessor, allBindingsAccessor, viewModel) {
      var progressbar, unitAdder,
        _this = this;
      unitAdder = $(element);
      progressbar = $(".ui-progressbar", unitAdder.parent()).fadeOut();
      unitAdder.find("form").fileupload({
        dataType: "json",
        dropZone: unitAdder,
        sequentialUploads: true,
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
          return exports.serviceReact(data.result, function(mdl) {
            console.log(mdl);
            return viewModel.addUnit(mdl);
          });
        }
      });
      return {
        success: function(data, textStatus, jqXHR) {
          return exports.serviceReact(data, function(mdl) {
            return console.log(mdl);
          });
        },
        error: function(data, textStatus, jqXHR) {
          return alert("Error");
        }
      };
    },
    update: function(element, valueAccessor, allBindingsAccessor, viewModel) {
      return console.log("updated");
    }
  };

}).call(this);
