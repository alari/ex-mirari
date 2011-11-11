exports = this
$ = exports.jQuery
exports.serviceReact = (jsonData, alertsElement, callback) ->
  if jsonData.srv.redirect?
    return window.location.href = jsonData.srv.redirect

  $(alertsElement).slideUp(200).empty();

  if jsonData.srv.alerts?
    $(alertsElement).append(jsonData.srv.alerts).slideDown(400)

  if jsonData.srv.ok?
    callback jsonData.mdl