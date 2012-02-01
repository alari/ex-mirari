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

    UnitUtils.addExternalUnit = function(container) {
      var url,
        _this = this;
      url = prompt("YouTube, Russia.Ru");
      if (!url) return null;
      return $.ajax("/p/addExternal", {
        type: "post",
        dataType: "json",
        data: {
          url: url
        },
        success: function(data, textStatus, jqXHR) {
          return serviceReact(data, function(mdl) {
            return _this.addUnitJson(container, mdl);
          });
        },
        error: function(data, textStatus, jqXHR) {
          return alert("Error");
        }
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

    return UnitUtils;

  })();

}).call(this);