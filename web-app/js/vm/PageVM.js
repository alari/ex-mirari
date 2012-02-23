(function() {
  var PageEditAct, PageInnersAct, exports,
    __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; };

  exports = this;

  exports.PageVM = (function() {

    function PageVM() {
      var _this = this;
      this.inners = ko.observableArray([]);
      this.tags = ko.observableArray([]);
      this.title = ko.observable("");
      this.id = ko.observable("");
      this.type = ko.observable("page");
      this.draft = ko.observable(true);
      this.thumbSrc = ko.observable("");
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
      if (typeof TagEditAct !== "undefined" && TagEditAct !== null) {
        this.tagAct = new TagEditAct(this);
      }
      this.editAct = new PageEditAct(this);
      this.innersAct = new PageInnersAct(this);
    }

    PageVM.prototype.unitTmpl = function(unit) {
      return "edit_" + unit.type;
    };

    PageVM.prototype.toJson = function() {
      return ko.mapping.toJSON(this, {
        ignore: ["_parent", "toJson", "avatar", "innersCount", "innersVisible", "contentVisible", "tagAct", "editAct", "innersAct", "uniqueName"]
      });
    };

    PageVM.prototype.fromJson = function(json) {
      var t, u, _i, _j, _len, _len2, _ref, _ref2, _results;
      this.inners.removeAll();
      this.tags.removeAll();
      this.title(json.title);
      this.id(json.id);
      this.type(json.type);
      this.draft(json.draft);
      this.thumbSrc(json.thumbSrc);
      _ref = json.inners;
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        u = _ref[_i];
        this.innersAct.addUnit(u);
      }
      _ref2 = json.tags;
      _results = [];
      for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
        t = _ref2[_j];
        _results.push(this.tagAct.addTag(t));
      }
      return _results;
    };

    return PageVM;

  })();

  PageInnersAct = (function() {

    function PageInnersAct(vm) {
      this.vm = vm;
      this.showAllContent = __bind(this.showAllContent, this);
      this.hideAllContent = __bind(this.hideAllContent, this);
      this.showAllInners = __bind(this.showAllInners, this);
      this.hideAllInners = __bind(this.hideAllInners, this);
      this.addRenderInnersUnit = __bind(this.addRenderInnersUnit, this);
      this.addTextUnit = __bind(this.addTextUnit, this);
      this.addExternalUnit = __bind(this.addExternalUnit, this);
      this.addUnit = __bind(this.addUnit, this);
    }

    PageInnersAct.prototype.addUnit = function(unitJson) {
      return UnitUtils.addUnitJson(this.vm, unitJson);
    };

    PageInnersAct.prototype.addExternalUnit = function() {
      return UnitUtils.addExternalUnit(this.vm);
    };

    PageInnersAct.prototype.addTextUnit = function() {
      return UnitUtils.addTextUnit(this.vm);
    };

    PageInnersAct.prototype.addRenderInnersUnit = function() {
      return UnitUtils.addRenderInnersUnit(this.vm);
    };

    PageInnersAct.prototype.hideAllInners = function() {
      var node, _i, _len, _ref, _results;
      _ref = this.vm.inners();
      _results = [];
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        node = _ref[_i];
        _results.push(UnitUtils.walk(node, function(n) {
          if (n.innersCount() > 0) return n.innersVisible(false);
        }));
      }
      return _results;
    };

    PageInnersAct.prototype.showAllInners = function() {
      var node, _i, _len, _ref, _results;
      _ref = this.vm.inners();
      _results = [];
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        node = _ref[_i];
        _results.push(UnitUtils.walk(node, function(n) {
          if (n.innersCount() > 0) return n.innersVisible(true);
        }));
      }
      return _results;
    };

    PageInnersAct.prototype.hideAllContent = function() {
      var node, _i, _len, _ref, _results;
      _ref = this.vm.inners();
      _results = [];
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        node = _ref[_i];
        _results.push(UnitUtils.walk(node, function(n) {
          return n.contentVisible(false);
        }));
      }
      return _results;
    };

    PageInnersAct.prototype.showAllContent = function() {
      var node, _i, _len, _ref, _results;
      _ref = this.vm.inners();
      _results = [];
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        node = _ref[_i];
        _results.push(UnitUtils.walk(node, function(n) {
          return n.contentVisible(true);
        }));
      }
      return _results;
    };

    return PageInnersAct;

  })();

  PageEditAct = (function() {

    function PageEditAct(vm) {
      this.vm = vm;
      this.submit = __bind(this.submit, this);
      this.submitPub = __bind(this.submitPub, this);
      this.submitDraft = __bind(this.submitDraft, this);
      this.saveAndContinue = __bind(this.saveAndContinue, this);
    }

    PageEditAct.prototype.saveAndContinue = function() {
      var _t,
        _this = this;
      if (UnitUtils.isEmpty(this.vm)) return false;
      _t = this.vm;
      return jsonPostReact("saveAndContinue", {
        ko: this.vm.toJson()
      }, function(mdl) {
        return _t.fromJson(mdl.page);
      });
    };

    PageEditAct.prototype.submitDraft = function() {
      this.vm.draft(true);
      return this.submit();
    };

    PageEditAct.prototype.submitPub = function() {
      this.vm.draft(false);
      return this.submit();
    };

    PageEditAct.prototype.submit = function() {
      if (UnitUtils.isEmpty(this.vm)) return false;
      return jsonPostReact("save", {
        draft: this.vm.draft(),
        ko: this.vm.toJson()
      }, function(mdl) {
        return console.log(mdl);
      });
    };

    return PageEditAct;

  })();

}).call(this);
