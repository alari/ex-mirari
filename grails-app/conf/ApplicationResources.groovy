modules = {
    twitterBootstrap {
        resource url: "/css/bootstrap.css"
    }
    twitterTabs {
        resource url: "/js/vendor/twitter/bootstrap-tabs.js"
        dependsOn "twitterBootstrap", "jquery"
    }
    twitterDropdown {
        resource url: "/js/vendor/twitter/bootstrap-dropdown.js"
        dependsOn "twitterBootstrap", "jquery"
    }
    twitterAlerts {
        resource url: "/js/vendor/twitter/bootstrap-alerts.js"
        dependsOn "twitterBootstrap", "jquery"
    }
    twitterTwipsy {
        resource url: "/js/vendor/twitter/bootstrap-twipsy.js"
        dependsOn "twitterBootstrap", "jquery"
    }
    jqueryUi {
        resource url: "http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.16/jquery-ui.min.js"
        resource url: "http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.16/themes/base/jquery-ui.css"
        dependsOn "jquery"
    }
    fileUploader {
        resource url: "/js/vendor/uploadr/jquery.iframe-transport.js", bundle: "uploadr-transport"
        resource url: "/js/vendor/uploadr/jquery.fileupload.js", bundle: "uploadr"
        dependsOn "jqueryUi"
    }
    mirariStyles {
        resource url: "/css/styles.css"
    }
    mirariAlerts {
        resource url: "/js/Alerts.js"
        dependsOn "ko"
    }
    mirariAvatarUpload {
        resource url: "/css/upload-avatar.css"
        resource url: "/js/avatar.js"
        dependsOn "fileUploader"
    }
    vm_pageEdit {
        resource url: "/js/vm/PageEditVM.js"
        resource url: "/js/binding/Binding.PageFileUpload.js"
        dependsOn "fileUploader", "vm_unit", "koMapping", "vm_tag", "unitUtils" , "vm_avatar"
    }
    vm_unit {
        resource url: "/js/vm/UnitVM.js"
        dependsOn "ko_sortableInners", "unitUtils"
    }
    vm_tag {
        resource url: "/js/vm/TagVM.js"
        dependsOn "ko"
    }
    vm_avatar {
        resource url: "/js/vm/AvatarVM.js"
        dependsOn "ko"
    }
    ko_sortableInners {
        resource url: "/js/binding/Binding.SortableInners.js"
        resource url: "/css/sortable-inners.css"
        dependsOn "ko", "jqueryUi"
    }
    ko_fixFloat {
        resource url: "/js/binding/Binding.FixFloat.js"
        dependsOn "ko"
    }
    ko_autoResize {
        resource url: "/js/binding/Binding.AutoResize.js"
        resource url: "/js/vendor/autoResize.js"
        dependsOn "ko", "jquery"
    }
    unitUtils {
        resource url: "/js/UnitUtils.js"
    }
    autocomplete {
        resource url: "/js/binding/Binding.Autocomplete.js"
        dependsOn "ko", "jqueryUi"
    }
    mirariUnitAdd {
        resource url: "/css/unit-add.css"
        dependsOn "vm_pageEdit"
    }
    jqueryTmpl {
        resource url: "/js/vendor/ko/jquery.tmpl.1.0.0pre.js"
        dependsOn "jquery"
    }
    ko {
        resource url: "/js/vendor/ko/knockout-2.0.0.js"
        dependsOn "jqueryTmpl"
    }
    koMapping {
        resource url: "/js/vendor/ko/knockout-mapping.2.0.3.js"
        dependsOn "ko"
    }
    mediaelement {
        resource url: "/js/vendor/mediaelement/mediaelement-and-player.min.js"
        resource url: "/js/vendor/mediaelement/mediaelementplayer.min.css"
        resource url: "/js/binding/Binding.Audio.js"
        dependsOn "jquery", "ko"
    }
    aloha {
        //resource url: "http://aloha.mirari.ws/lib/aloha.js", attrs: ["data-aloha-plugins":"common/format,common/list,common/paste,common/link,common/align,common/undo"]
        //resource "http://aloha.mirari.ws/css/aloha.css"
        resource url: "/js/binding/Binding.Aloha.js"
        dependsOn "jquery", "ko"
    }
}