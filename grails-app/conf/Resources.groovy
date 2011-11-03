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
  jqueryUi {
    resource url: "http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.16/jquery-ui.min.js"
    resource url: "http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.16/themes/base/jquery-ui.css"
    dependsOn "jquery"
  }
  fileUploader {
    resource url: "/js/uploadr/jquery.iframe-transport.js"
    resource url: "/js/uploadr/jquery.fileupload.js"
    dependsOn "jqueryUi"
  }
  mirariAvatarUpload {
    resource url: "/css/upload-avatar.css"
    resource url: "/js/upload-avatar.js"
    dependsOn "fileUploader"
  }
    mirariUnitUpload {
        resource url: "/js/upload-unit.js"
        dependsOn "fileUploader"
    }
    mirariServiceReact {
        resource url: "/js/service-react.js"
        dependsOn: "jquery"
    }
    mirariUnitAdd {
        resource url: "/js/unit-edit.js"
        dependsOn "fileUploader", "mirariServiceReact"
    }
}