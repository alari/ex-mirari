(function() {
  var $, exports;
  var __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; }, __hasProp = Object.prototype.hasOwnProperty, __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor; child.__super__ = parent.prototype; return child; };

  exports = this;

  $ = exports.jQuery;

  $(function() {
    exports.UnitEdit = (function() {

      function UnitEdit(_parent, json) {
        this._parent = _parent;
        this.title = ko.observable(json.title);
        this.id = json.id;
        this.type = json.type;
        this.params = json.params;
      }

      return UnitEdit;

    })();
    exports.UnitEditImage = (function() {

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
    exports.UnitEditImageColl = (function() {

      __extends(UnitEditImageColl, UnitEdit);

      function UnitEditImageColl() {
        UnitEditImageColl.__super__.constructor.apply(this, arguments);
      }

      return UnitEditImageColl;

    })();
    return exports.UnitEditText = (function() {

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
