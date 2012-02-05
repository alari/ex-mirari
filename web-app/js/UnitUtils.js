(function() {
  var exports;

  exports = this;

  exports.UnitUtils = (function() {

    function UnitUtils() {}

    UnitUtils.addUnitJson = function(container, unitJson) {
      var u, unit, _i, _len, _ref;
      unit = new UnitVM(container, unitJson);
      if (unitJson.inners && unitJson.inners.length) {
        _ref = unitJson.inners;
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          u = _ref[_i];
          this.addUnitJson(unit, u);
        }
      }
      return container.inners.push(unit);
    };

    UnitUtils.addTextUnit = function(container) {
      return this.addUnitJson(container, {
        type: "text",
        id: null,
        text: "",
        title: null
      });
    };

    UnitUtils.addRenderInnersUnit = function(container) {
      return this.addUnitJson(container, {
        type: "renderInners",
        id: null,
        title: null
      });
    };

    UnitUtils.addExternalUnit = function(container) {
      var url,
        _this = this;
      url = prompt("YouTube, Russia.Ru");
      if (!url) return null;
      return jsonPostReact("/p/addExternal", {
        url: url
      }, function(mdl) {
        return _this.addUnitJson(container, mdl);
      });
    };

    UnitUtils.walk = function(unit, fnc) {
      var u, _i, _len, _ref, _results;
      fnc(unit);
      _ref = unit.inners();
      _results = [];
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        u = _ref[_i];
        _results.push(this.walk(u, fnc));
      }
      return _results;
    };

    UnitUtils.isEmpty = function(container) {
      var u, _i, _len, _ref;
      _ref = container.inners();
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        u = _ref[_i];
        if (!u._destroy) if (!this.isEmpty(u)) return false;
      }
      if (container instanceof UnitVM) {
        if (container.type !== "text") return false;
        return !container.params.text;
      }
      return true;
    };

    return UnitUtils;

  })();

}).call(this);
