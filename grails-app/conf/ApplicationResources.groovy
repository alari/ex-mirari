modules = {
    twitterBootstrap {
        resource url: "/css/bootstrap/css/bootstrap.min.css"
        resource url: "/css/bootstrap/js/bootstrap.min.js"
        dependsOn "jquery"
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
        dependsOn "ko", "ko_fadeOut"
    }
    css_announcesGrid {
        resource url: "/css/announces-grid.css"
    }
    mirariAvatarUpload {
        resource url: "/css/upload-avatar.css"
        resource url: "/js/avatar.js"
        dependsOn "fileUploader"
    }
    vm_pageEdit {
        resource url: "/js/vm/PageEditVM.js"
        resource url: "/js/binding/Binding.PageFileUpload.js"
        dependsOn "fileUploader", "vm_unit", "ko_mapping", "vm_tag", "unitUtils" , "vm_avatar"
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
    vm_comment {
        resource url: "/js/vm/CommentVM.js"
        dependsOn "ko"
    }
    vm_reply {
        resource url: "/js/vm/ReplyVM.js"
        dependsOn "ko"
    }
    vm_pageComments {
        resource url: "/js/vm/PageCommentsVM.js"
        dependsOn "ko", "vm_comment", "vm_reply"
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
    ko_carousel {
        resource url: "/js/binding/Binding.Carousel.js"
        dependsOn "ko", "jquery", "twitterBootstrap"
    }
    ko_fadeOut {
        resource url: "/js/binding/Binding.FadeOut.js"
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
    ko {
        resource url: "/js/vendor/ko/knockout-2.1.0.pre.js"
        //dependsOn "jqueryTmpl"
    }
    ko_mapping {
        resource url: "/js/vendor/ko/knockout-mapping.2.0.3.js"
        dependsOn "ko"
    }
    ko_editables {
        resource url: "/js/vendor/ko/ko.editables.js"
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