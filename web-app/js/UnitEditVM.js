(function() {
  var $, exports;
  var __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; }, __hasProp = Object.prototype.hasOwnProperty, __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor; child.__super__ = parent.prototype; return child; };

  exports = this;

  $ = exports.jQuery;

  $(function() {
    var ko;
    exports.UnitEdit = (function() {

      function UnitEdit(_parent, json) {
        var _this = this;
        this._parent = _parent;
        this.title = ko.observable(json.title);
        this.titleVisible = ko.dependentObservable(function() {
          return _this._parent.inners().length > 1;
        });
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
    exports.UnitEditText = (function() {

      __extends(UnitEditText, UnitEdit);

      function UnitEditText(_parent, json) {
        this._parent = _parent;
        UnitEditText.__super__.constructor.call(this, this._parent, json);
        this.text = json.text;
      }

      return UnitEditText;

    })();
    ko = exports.ko;
    return ko.bindingHandlers.aloha = {
      init: function(element, valueAccessor, allBindingsAccessor, viewModel) {
        $(element).attr("contenteditable", true);
        $(element).focus(function() {
          var $this;
          $this = $(this);
          return $this.data('html-before', $this.html());
        });
        return $(element).bind('blur keyup paste', function() {
          var $this;
          $this = $(this);
          if ($this.data('html-before') !== $this.html()) {
            $this.data('html-before', $this.html());
            viewModel.text = $this.html();
            return $this.trigger('change');
          }
        });
      }
    };
  });

}).call(this);
