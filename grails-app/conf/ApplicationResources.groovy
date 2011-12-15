modules = {
    twitterBootstrap {
        resource url: "/css/bootstrap.css"
    }
    twitterTabs {
        resource url: "http://twitter.github.com/bootstrap/1.4.0/bootstrap-tabs.js"
        dependsOn "twitterBootstrap", "jquery"
    }
    twitterDropdown {
        resource url: "http://twitter.github.com/bootstrap/1.4.0/bootstrap-dropdown.js"
        dependsOn "twitterBootstrap", "jquery"
    }
    twitterAlerts {
        resource url: "http://twitter.github.com/bootstrap/1.4.0/bootstrap-alerts.js"
        dependsOn "twitterBootstrap", "jquery"
    }
    twitterTwipsy {
        resource url: "http://twitter.github.com/bootstrap/1.4.0/bootstrap-twipsy.js"
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
    mirariStyles {
        resource url: "/css/styles.css"
    }
    mirariAvatarUpload {
        resource url: "/css/upload-avatar.css"
        resource url: "/js/avatar.js"
        dependsOn "fileUploader"
    }
    mirariServiceReact {
        resource url: "/js/service-react.js"
        dependsOn: "jquery"
    }
    vm_pageEdit {
        resource url: "/js/PageEditVM.js"
        dependsOn "fileUploader", "mirariServiceReact", "vm_unitEdit", "koMapping"
    }
    vm_unitEdit {
        resource url: "/js/UnitEditVM.js"
        dependsOn "ko_sortableInners"
    }
    ko_sortableInners {
        resource url: "/js/sortableInners.js"
        dependsOn "ko", "jqueryUi"
    }
    mirariUnitAdd {
        resource url: "/css/unit-add.css"
        dependsOn "vm_pageEdit"
    }
    jqueryTmpl {
        resource url: "/js/jquery.tmpl.1.0.0pre.js"
        dependsOn "jquery"
    }
    ko {
        resource url: "/js/ko/knockout-1.3.0beta.js"
        dependsOn "jqueryTmpl"
    }
    koMapping {
        resource url: "/js/ko/knockout-mapping.121111.js"
        dependsOn "ko"
    }
}