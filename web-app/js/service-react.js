(function() {
  var $, exports;

  exports = this;

  $ = exports.jQuery;

  exports.serviceReact = function(jsonData, alertsElement, callback) {
    if (jsonData.srv.redirect != null) {
      return window.location.href = jsonData.srv.redirect;
    }
    $(alertsElement).slideUp(200).empty();
    if (jsonData.srv.alerts != null) {
      $(alertsElement).append(jsonData.srv.alerts).slideDown(400);
    }
    if (jsonData.srv.ok != null) return callback(jsonData.mdl);
  };

}).call(this);
