(function() {
  var $, exports,
    __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; },
    __hasProp = Object.prototype.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor; child.__super__ = parent.prototype; return child; };

  exports = this;

  $ = exports.jQuery;

  $(function() {
    exports.UnitEdit = (function() {

      function UnitEdit(_parent, json) {
        var _this = this;
        this._parent = _parent;
        this._titleVisible = __bind(this._titleVisible, this);
        this.envelopTmplName = __bind(this.envelopTmplName, this);
        this.sortTo = __bind(this.sortTo, this);
        this.remove = __bind(this.remove, this);
        this.title = ko.observable(json.title);
        this.id = json.id;
        this.type = json.type;
        this.params = json.params;
        this.inners = ko.observableArray([]);
        this.titleVisible = ko.dependentObservable(function() {
          return _this._titleVisible();
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

      UnitEdit.prototype.remove = function() {
        return this._parent.inners.destroy(this);
      };

      UnitEdit.prototype.sortTo = function(newParent, position) {
        if (position >= 0) {
          this._parent.inners.remove(this);
          this._parent = newParent;
          return this._parent.inners.splice(position, 0, this);
        }
      };

      UnitEdit.prototype.envelopTmplName = function() {
        return "unitEdit";
      };

      UnitEdit.prototype._titleVisible = function() {
        if (this._parent.innersCount() > 1 || this._parent instanceof UnitEdit) {
          return true;
        }
      };

      return UnitEdit;

    })();
    exports.UnitEditImage = (function() {

      __extends(UnitEditImage, UnitEdit);

      function UnitEditImage() {
        this.tmplName = __bind(this.tmplName, this);
        UnitEditImage.__super__.constructor.apply(this, arguments);
      }

      UnitEditImage.prototype.tmplName = function() {
        if (this._parent.inners().length > 1 && false) return "editImage_tiny";
      };

      UnitEditImage.prototype._titleVisible = function() {
        return false;
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
    exports.UnitEditText = (function() {

      __extends(UnitEditText, UnitEdit);

      function UnitEditText(_parent, json) {
        this._parent = _parent;
        UnitEditText.__super__.constructor.call(this, this._parent, json);
        this.text = json.text;
      }

      return UnitEditText;

    })();
    return exports.UnitEditAudio = (function() {

      __extends(UnitEditAudio, UnitEdit);

      function UnitEditAudio() {
        UnitEditAudio.__super__.constructor.apply(this, arguments);
      }

      UnitEditAudio.prototype._titleVisible = function() {
        return false;
      };

      return UnitEditAudio;

    })();
  });

}).call(this);
