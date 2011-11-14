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
      }

      UnitEditViewModel.prototype.contents = ko.observableArray([]);

      UnitEditViewModel.prototype.addUnit = function(unitJsonObject) {
        var type;
        type = unitJsonObject.type;
        if (type === "Image") {
          this.contents.push(new UnitEditImage(unitJsonObject));
        }
        return console.log(this.contents());
      };

      UnitEditViewModel.prototype.unitTmpl = function(unit) {
        return unit.tmplName;
      };

      return UnitEditViewModel;

    })();
    UnitEdit = (function() {

      function UnitEdit(obj) {
        this.obj = obj;
        this.title = ko.observable(this.obj.title);
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
