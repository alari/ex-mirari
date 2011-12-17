(function() {
  var $, AlertMessageVM, exports;

  exports = this;

  $ = exports.jQuery;

  exports.AlertsVM = (function() {

    function AlertsVM() {
      this.alerts = ko.observableArray([]);
    }

    AlertsVM.prototype.alert = function(json) {
      return this.alerts.push(new AlertMessageVM(json));
    };

    AlertsVM.prototype.success = function(message) {
      return this.alert({
        message: message,
        level: "success"
      });
    };

    AlertsVM.prototype.info = function(message) {
      return this.alert({
        message: message,
        level: "info"
      });
    };

    AlertsVM.prototype.warning = function(message) {
      return this.alert({
        message: message,
        level: "warning"
      });
    };

    AlertsVM.prototype.error = function(message) {
      return this.alert({
        message: message,
        level: "error"
      });
    };

    return AlertsVM;

  })();

  AlertMessageVM = (function() {

    function AlertMessageVM(json) {
      this.level = json.level;
      this.message = json.message;
    }

    AlertMessageVM.prototype.remove = function() {
      return alertsVM.alerts.remove(this);
    };

    return AlertMessageVM;

  })();

  exports.alertsVM = new AlertsVM();

  exports.serviceReact = function(json, callback) {
    var alert, _i, _len, _ref;
    if (json.srv.redirect != null) return window.location.href = json.srv.redirect;
    if (json.srv.alerts != null) {
      _ref = json.srv.alerts;
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        alert = _ref[_i];
        alertsVM.alert(alert);
      }
    }
    if (json.srv.ok != null) callback(json.mdl);
    if (!(json.srv.ok != null) && !(json.srv.alerts != null)) {
      return alertsVM.error("Ajax Error");
    }
  };

}).call(this);
