(function() {
  var exports,
    __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; };

  exports = this;

  exports.CompoundUnitVM = (function() {

    function CompoundUnitVM(unit) {
      this.unit = unit;
      this.getInner = __bind(this.getInner, this);
      this.type = this.unit.params.type;
      if (!this.unit.innersCount()) {
        if (this.type === "poetry") UnitUtils.addTextUnit(this.unit);
      }
    }

    CompoundUnitVM.prototype.getInner = function(type) {
      var u, _i, _len, _ref;
      _ref = this.unit.inners();
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        u = _ref[_i];
        if (u.type === type) return u;
      }
    };

    return CompoundUnitVM;

  })();

  exports.CompoundType = (function() {

    function CompoundType() {}

    CompoundType.poetry = 1;

    return CompoundType;

  })();

}).call(this);
