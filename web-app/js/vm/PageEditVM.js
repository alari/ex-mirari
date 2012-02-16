(function() {
  var $, exports,
    __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; },
    __hasProp = Object.prototype.hasOwnProperty,
    __indexOf = Array.prototype.indexOf || function(item) { for (var i = 0, l = this.length; i < l; i++) { if (__hasProp.call(this, i) && this[i] === item) return i; } return -1; };

  exports = this;

  $ = exports.jQuery;

  exports.PageEditVM = (function() {

    function PageEditVM() {
      this.showAllContent = __bind(this.showAllContent, this);
      this.hideAllContent = __bind(this.hideAllContent, this);
      this.showAllInners = __bind(this.showAllInners, this);
      this.hideAllInners = __bind(this.hideAllInners, this);
      this.submit = __bind(this.submit, this);
      this.submitPub = __bind(this.submitPub, this);
      this.submitDraft = __bind(this.submitDraft, this);
      this.saveAndContinue = __bind(this.saveAndContinue, this);
      this.addRenderInnersUnit = __bind(this.addRenderInnersUnit, this);
      this.addTextUnit = __bind(this.addTextUnit, this);
      this.addExternalUnit = __bind(this.addExternalUnit, this);
      this.tagInputKey = __bind(this.tagInputKey, this);
      this.addNewTag = __bind(this.addNewTag, this);
      this.addTag = __bind(this.addTag, this);
      this.addUnit = __bind(this.addUnit, this);
      var _this = this;
      this._action = null;
      this.inners = ko.observableArray([]);
      this.tags = ko.observableArray([]);
      this.title = ko.observable("");
      this.id = ko.observable();
      this.type = ko.observable("page");
      this.draft = ko.observable(true);
      this.innersCount = ko.computed(function() {
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
      this.avatar = new AvatarVM();
    }

    PageEditVM.prototype.addUnit = function(unitJson) {
      return UnitUtils.addUnitJson(this, unitJson);
    };

    PageEditVM.prototype.addTag = function(json) {
      return this.tags.push(new TagVM(this).fromJson(json));
    };

    PageEditVM.prototype.addNewTag = function(data, event) {
      var value, _ref;
      value = (_ref = event.target) != null ? _ref.value : void 0;
      if (value) this.tags.push(new TagVM(this, value));
      return event.target.value = "";
    };

    PageEditVM.prototype.tagInputKey = function(data, event) {
      var input, keys, stops, _ref;
      keys = {
        backspace: [8],
        enter: [13],
        space: [32],
        comma: [44, 188],
        tab: [9]
      };
      stops = [13, 9];
      input = event.target;
      if (!input.value && event.which === 8) {
        this.tags.remove(this.tags()[this.tags().length - 1]);
      }
      if (input.value && (_ref = event.which, __indexOf.call(stops, _ref) >= 0)) {
        this.tags.push(new TagVM(this, input.value));
        input.value = "";
      }
      return true;
    };

    PageEditVM.prototype.addExternalUnit = function() {
      return UnitUtils.addExternalUnit(this);
    };

    PageEditVM.prototype.addTextUnit = function() {
      return UnitUtils.addTextUnit(this);
    };

    PageEditVM.prototype.addRenderInnersUnit = function() {
      return UnitUtils.addRenderInnersUnit(this);
    };

    PageEditVM.prototype.unitTmpl = function(unit) {
      return "edit_" + unit.type;
    };

    PageEditVM.prototype.toJSON = function() {
      return ko.mapping.toJSON(this, {
        ignore: ["_parent", "_action", "toJSON", "avatar", "innersCount", "innersVisible", "contentVisible"]
      });
    };

    PageEditVM.prototype.fromJson = function(json) {
      var t, u, _i, _j, _len, _len2, _ref, _ref2, _results;
      this.inners.removeAll();
      this.tags.removeAll();
      this.title(json.title);
      this.id(json.id);
      this.type(json.type);
      this.draft(json.draft);
      _ref = json.inners;
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        u = _ref[_i];
        this.addUnit(u);
      }
      _ref2 = json.tags;
      _results = [];
      for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
        t = _ref2[_j];
        _results.push(this.addTag(t));
      }
      return _results;
    };

    PageEditVM.prototype.saveAndContinue = function() {
      var _t,
        _this = this;
      if (UnitUtils.isEmpty(this)) return false;
      _t = this;
      return jsonPostReact("saveAndContinue", {
        ko: this.toJSON()
      }, function(mdl) {
        _t.fromJson(mdl.page);
        return _t.id(mdl.page.id);
      });
    };

    PageEditVM.prototype.submitDraft = function() {
      this.draft(true);
      return this.submit();
    };

    PageEditVM.prototype.submitPub = function() {
      this.draft(false);
      return this.submit();
    };

    PageEditVM.prototype.submit = function() {
      if (UnitUtils.isEmpty(this)) return false;
      return jsonPostReact(this._action, {
        draft: this.draft(),
        ko: this.toJSON()
      }, function(mdl) {
        return console.log(mdl);
      });
    };

    PageEditVM.prototype.hideAllInners = function() {
      var node, _i, _len, _ref, _results;
      _ref = this.inners();
      _results = [];
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        node = _ref[_i];
        _results.push(UnitUtils.walk(node, function(n) {
          if (n.innersCount() > 0) return n.innersVisible(false);
        }));
      }
      return _results;
    };

    PageEditVM.prototype.showAllInners = function() {
      var node, _i, _len, _ref, _results;
      _ref = this.inners();
      _results = [];
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        node = _ref[_i];
        _results.push(UnitUtils.walk(node, function(n) {
          if (n.innersCount() > 0) return n.innersVisible(true);
        }));
      }
      return _results;
    };

    PageEditVM.prototype.hideAllContent = function() {
      var node, _i, _len, _ref, _results;
      _ref = this.inners();
      _results = [];
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        node = _ref[_i];
        _results.push(UnitUtils.walk(node, function(n) {
          return n.contentVisible(false);
        }));
      }
      return _results;
    };

    PageEditVM.prototype.showAllContent = function() {
      var node, _i, _len, _ref, _results;
      _ref = this.inners();
      _results = [];
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        node = _ref[_i];
        _results.push(UnitUtils.walk(node, function(n) {
          return n.contentVisible(true);
        }));
      }
      return _results;
    };

    return PageEditVM;

  })();

}).call(this);
