package mirari.infra

class AlertsTagLib {
    def alertsService

    def alerts = {attrs ->
        out << g.render(template: "/includes/alerts", model: [alerts: alertsService.getAlerts(flash)])
        alertsService.clean(flash)
    }
}
