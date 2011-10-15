package mirari

class AlertsTagLib {
  def alertsService

  def alerts = {attrs ->
    alertsService.getAlerts(flash).each {
      out << /<div data-alert="alert" class="alert-message / + it.level + /">/
      out << /<a class="close" href="#">&times;</ + '/a>'
      out << "<p>${message(code: it.code, params: it.params)}</p></div>"
    }
    alertsService.clean(flash)
  }
}
