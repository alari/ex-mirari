(function() {
  var $, exports;
  var __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; }, __hasProp = Object.prototype.hasOwnProperty, __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor; child.__super__ = parent.prototype; return child; };

  exports = this;

  $ = exports.jQuery;

  $(function() {
    var UnitEdit, UnitEditImage, UnitEditImageColl, UnitEditText;
    exports.UnitEditViewModel = (function() {

      function UnitEditViewModel() {
        this.submit = __bind(this.submit, this);
        this.submitDraft = __bind(this.submitDraft, this);
        this.addTextUnit = __bind(this.addTextUnit, this);
        this.addUnit = __bind(this.addUnit, this);
        var _this = this;
        this._action = null;
        this.units = ko.observableArray([]);
        this._title = ko.observable();
        this.title = ko.dependentObservable({
          read: function() {
            if (_this.units().length === 1) {
              return _this.units()[0].title();
            } else {
              return _this._title();
            }
          },
          write: function(v) {
            if (_this.units().length === 1) {
              return _this.units()[0].title(v);
            } else {
              return _this._title(v);
            }
          }
        });
        this.id = ko.dependentObservable(function() {
          if (_this.units().length === 1) return _this.units()[0].id;
        });
      }

      UnitEditViewModel.prototype.addUnit = function(unitJson) {
        var type;
        type = unitJson.type;
        if (type === "Image") this.units.push(new UnitEditImage(this, unitJson));
        if (type === "Text") {
          return this.units.push(new UnitEditText(this, unitJson));
        }
      };

      UnitEditViewModel.prototype.addTextUnit = function() {
        return this.addUnit({
          type: "Text",
          id: null,
          text: "",
          title: null
        });
      };

      UnitEditViewModel.prototype.unitTmpl = function(unit) {
        if (unit.tmplName && unit.tmplName()) {
          return unit.tmplName();
        } else {
          return "edit" + unit.type;
        }
      };

      UnitEditViewModel.prototype.toJSON = function() {
        return ko.mapping.toJSON(this, {
          ignore: ["_title", "_parent", "_action", "tmplName", "toJSON"]
        });
      };

      UnitEditViewModel.prototype.submitDraft = function() {
        return this.submit(true);
      };

      UnitEditViewModel.prototype.submit = function(draft) {
        return $.ajax(this._action, {
          type: "post",
          dataType: "json",
          data: {
            draft: draft === true ? true : false,
            ko: this.toJSON()
          },
          success: function(data, textStatus, jqXHR) {
            return exports.serviceReact(data, "#alerts", function(mdl) {
              return console.log(mdl);
            });
          },
          error: function(data, textStatus, jqXHR) {
            return alert("Error");
          }
        });
      };

      return UnitEditViewModel;

    })();
    UnitEdit = (function() {

      function UnitEdit(_parent, json) {
        this._parent = _parent;
        this.title = ko.observable(json.title);
        this.id = json.id;
        this.type = json.type;
        this.params = json.params;
      }

      return UnitEdit;

    })();
    UnitEditImage = (function() {

      __extends(UnitEditImage, UnitEdit);

      function UnitEditImage() {
        this.tmplName = __bind(this.tmplName, this);
        UnitEditImage.__super__.constructor.apply(this, arguments);
      }

      UnitEditImage.prototype.tmplName = function() {
        if (this._parent.units().length > 1) return "editImage_tiny";
      };

      return UnitEditImage;

    })();
    UnitEditImageColl = (function() {

      __extends(UnitEditImageColl, UnitEdit);

      function UnitEditImageColl() {
        UnitEditImageColl.__super__.constructor.apply(this, arguments);
      }

      return UnitEditImageColl;

    })();
    return UnitEditText = (function() {

      __extends(UnitEditText, UnitEdit);

      function UnitEditText(_parent, json) {
        this._parent = _parent;
        UnitEditText.__super__.constructor.call(this, this._parent, json);
        this.text = ko.observable(json.text);
      }

      return UnitEditText;

    })();
  });

}).call(this);
