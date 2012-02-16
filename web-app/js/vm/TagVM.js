(function() {
  var exports,
    __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; };

  exports = this;

  exports.TagVM = (function() {

    function TagVM(_parent, displayName) {
      this._parent = _parent;
      this.displayName = displayName;
      this.remove = __bind(this.remove, this);
      this.fromJson = __bind(this.fromJson, this);
      this.id = null;
    }

    TagVM.prototype.fromJson = function(json) {
      this.id = json.id;
      this.displayName = json.displayName;
      return this;
    };

    TagVM.prototype.remove = function() {
      return this._parent.tags.remove(this);
    };

    return TagVM;

  })();

}).call(this);
