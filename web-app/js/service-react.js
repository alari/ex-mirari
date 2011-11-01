function serviceReact(jsonData, alertsElement, callback) {
    if(jsonData.srv.redirect) {
        window.location.href = jsonData.srv.redirect;
        return;
    }
    $(alertsElement).slideUp(200).empty();
    if(jsonData.srv.alerts) {
        $(alertsElement).append(jsonData.srv.alerts).slideDown(400);
    }
    callback(jsonData.mdl);
}