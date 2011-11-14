(function() {
  var $, exports;
  var __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; }, __hasProp = Object.prototype.hasOwnProperty, __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor; child.__super__ = parent.prototype; return child; };

  exports = this;

  $ = exports.jQuery;

  $(function() {
    var UnitEdit, UnitEditImage;
    exports.UnitEditViewModel = (function() {

      function UnitEditViewModel() {
        this.addUnit = __bind(this.addUnit, this);
        var _this = this;
        this.contents = ko.observableArray([]);
        this._title = ko.observable();
        this.title = ko.dependentObservable({
          read: function() {
            if (_this.contents().length === 1) {
              return _this.contents()[0].title();
            } else {
              return _this._title();
            }
          },
          write: function(v) {
            if (_this.contents().length === 1) {
              return _this.contents()[0].title(v);
            } else {
              return _this._title(v);
            }
          }
        });
        this.id = ko.dependentObservable(function() {
          if (_this.contents().length === 1) return _this.contents()[0].id;
        });
      }

      UnitEditViewModel.prototype.addUnit = function(unitJsonObject) {
        var type;
        type = unitJsonObject.type;
        if (type === "Image") {
          return this.contents.push(new UnitEditImage(this, unitJsonObject));
        }
      };

      UnitEditViewModel.prototype.unitTmpl = function(unit) {
        return unit.tmplName;
      };

      return UnitEditViewModel;

    })();
    UnitEdit = (function() {

      function UnitEdit(vm, json) {
        this.vm = vm;
        this.title = ko.observable(json.title);
        this.id = json.id;
        this.container = json.container;
        this.type = json.type;
        this.params = json.params;
      }

      return UnitEdit;

    })();
    return UnitEditImage = (function() {

      __extends(UnitEditImage, UnitEdit);

      function UnitEditImage() {
        UnitEditImage.__super__.constructor.apply(this, arguments);
      }

      UnitEditImage.prototype.tmplName = "unitEditImage";

      return UnitEditImage;

    })();
  });

}).call(this);
