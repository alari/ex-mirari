(function() {
  var $, exports;
  var __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; }, __hasProp = Object.prototype.hasOwnProperty, __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor; child.__super__ = parent.prototype; return child; };

  exports = this;

  $ = exports.jQuery;

  $(function() {
    var UnitEdit, UnitEditImage, UnitEditImageCollection, UnitEditText;
    exports.UnitEditViewModel = (function() {

      function UnitEditViewModel() {
        this.submit = __bind(this.submit, this);
        this.submitDraft = __bind(this.submitDraft, this);
        this.addTextUnit = __bind(this.addTextUnit, this);
        this.addUnit = __bind(this.addUnit, this);
        var _this = this;
        this._action = null;
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
          this.contents.push(new UnitEditImage(this, unitJsonObject));
        }
        if (type === "Text") {
          return this.contents.push(new UnitEditText(this, unitJsonObject));
        }
      };

      UnitEditViewModel.prototype.addTextUnit = function() {
        return this.addUnit({
          type: "Text",
          id: null,
          container: this.id,
          params: {
            text: ko.observable()
          },
          title: null
        });
      };

      UnitEditViewModel.prototype.unitTmpl = function(unit) {
        return unit.tmplName();
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
            draft: draft ? true : false,
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
        this.container = json.container;
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
        if (this._parent.contents().length > 1) {
          return "unitTinyImageEdit";
        } else {
          return "unitEditImage";
        }
      };

      return UnitEditImage;

    })();
    UnitEditImageCollection = (function() {

      __extends(UnitEditImageCollection, UnitEdit);

      function UnitEditImageCollection() {
        this.tmplName = __bind(this.tmplName, this);
        UnitEditImageCollection.__super__.constructor.apply(this, arguments);
      }

      UnitEditImageCollection.prototype.tmplName = function() {
        return "unitEditImageCollection";
      };

      return UnitEditImageCollection;

    })();
    return UnitEditText = (function() {

      __extends(UnitEditText, UnitEdit);

      function UnitEditText() {
        this.tmplName = __bind(this.tmplName, this);
        UnitEditText.__super__.constructor.apply(this, arguments);
      }

      UnitEditText.prototype.tmplName = function() {
        return "unitEditText";
      };

      return UnitEditText;

    })();
  });

}).call(this);
