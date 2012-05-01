(function() {
  var exports,
    __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; };

  exports = this;

  exports.CompoundUnitVM = (function() {

    CompoundUnitVM.prototype.inner = {};

    function CompoundUnitVM(unit) {
      this.unit = unit;
      this.getInner = __bind(this.getInner, this);
      this.type = this.unit.params.type;
      if (!this.unit.innersCount()) {
        CompoundType[this.type].init(this);
      } else {
        CompoundType[this.type].restore(this);
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

    CompoundType.poetry = {
      init: function(compound) {
        var t;
        t = UnitUtils.addTextUnit(compound.unit);
        return compound.text = ko.computed({
          read: function() {
            return t.params.text;
          },
          write: function(v) {
            t.params.text = v;
            return console.log(v);
          }
        });
      },
      restore: function(compound) {
        return compound.text = compound.getInner("text");
      }
    };

    return CompoundType;

  })();

}).call(this);
