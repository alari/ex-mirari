package mirari

import org.codehaus.groovy.grails.web.servlet.FlashScope

class AlertsService {
  def transactional = false

  def alert(FlashScope flash, String level, String code, Map args) {
    if (!flash.alerts) {
      flash.alerts = []
    }
    ((List) flash.alerts).add([level: level, code: code, params: args])
  }

  def warning(FlashScope flash, String code, Map args = [:]) {
    alert(flash, "warning", code, args)
  }

  def error(FlashScope flash, String code, Map args = [:]) {
    alert(flash, "error", code, args)
  }

  def success(FlashScope flash, String code, Map args = [:]) {
    alert(flash, "success", code, args)
  }

  def info(FlashScope flash, String code, Map args = [:]) {
    alert(flash, "info", code, args)
  }

  List getAlerts(FlashScope flash) {
    List alerts = flash.alerts
    if (flash.error) alerts.add([code: flash.error, level: "error"])
    if (flash.message) alerts.add([code: flash.message, level: "info"])
    alerts
  }

  void clean(FlashScope flash) {
    flash.alerts = []
  }
}
