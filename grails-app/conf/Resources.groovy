modules = {
  twitterBootstrap {
    resource url: "http://twitter.github.com/bootstrap/1.3.0/bootstrap.min.css"
  }
  twitterTabs {
    resource url: "http://twitter.github.com/bootstrap/1.3.0/bootstrap-tabs.js"
    dependsOn "twitterBootstrap", "jquery"
  }
  twitterDropdown {
    resource url: "http://twitter.github.com/bootstrap/1.3.0/bootstrap-dropdown.js"
    dependsOn "twitterBootstrap", "jquery"
  }
  twitterAlerts {
    resource url: "http://twitter.github.com/bootstrap/1.3.0/bootstrap-alerts.js"
    dependsOn "twitterBootstrap", "jquery"
  }
}