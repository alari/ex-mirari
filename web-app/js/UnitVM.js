(function() {
  var $, exports,
    __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; };

  exports = this;

  $ = exports.jQuery;

  $(function() {
    return exports.UnitVM = (function() {

      function UnitVM(_parent, json) {
        var _this = this;
        this._parent = _parent;
        this.toggleInnersVisibility = __bind(this.toggleInnersVisibility, this);
        this.sortTo = __bind(this.sortTo, this);
        this.remove = __bind(this.remove, this);
        this.title = ko.observable(json.title);
        this.id = json.id;
        this.type = json.type;
        this.params = json.params || {};
        this.inners = ko.observableArray([]);
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
        this.innersVisible = ko.observable(true);
        this.contentVisible = ko.observable(true);
      }

      UnitVM.prototype.remove = function() {
        return this._parent.inners.destroy(this);
      };

      UnitVM.prototype.sortTo = function(newParent, position) {
        if (position >= 0) {
          this._parent.inners.remove(this);
          this._parent = newParent;
          return this._parent.inners.splice(position, 0, this);
        }
      };

      UnitVM.prototype.toggleInnersVisibility = function() {
        this.innersVisible(!this.innersVisible());
        return console.log(this.innersVisible());
      };

      return UnitVM;

    })();
  });

}).call(this);
