modules = {
    vendor_bootstrap {
        resource url: "/css/bootstrap/css/bootstrap.min.css"
        resource url: "/css/bootstrap/js/bootstrap.min.js"
        dependsOn "jquery"
    }
    jqueryUi {
        resource url: "http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.16/jquery-ui.min.js"
        resource url: "http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.16/themes/base/jquery-ui.css"
        dependsOn "jquery"
    }
    vendor_fileUpload {
        resource url: "/js/vendor/uploadr/jquery.iframe-transport.js", bundle: "uploadr-transport"
        resource url: "/js/vendor/uploadr/jquery.fileupload.js", bundle: "uploadr"
        dependsOn "jqueryUi"
    }
    vendor_mediaelement {
        resource url: "/js/vendor/mediaelement/mediaelement-and-player.min.js"
        resource url: "/js/vendor/mediaelement/mediaelementplayer.min.css"
        resource url: "/js/ko/audio.js"
        dependsOn "jquery", "ko"
    }

    css_default {
        resource url: "/css/styles.css"
    }
    mirariAlerts {
        resource url: "/js/Alerts.js"
        dependsOn "ko", "ko_fadeOut"
    }
    css_announcesGrid {
        resource url: "/css/announces-grid.css"
    }
    css_siteAvatarUpload {
        resource url: "/css/site-avatar-upload.css"
    }



    /*   VIEW MODELS   */
    vm_pageEdit {
        dependsOn "vm_tag", "vm_page", "ko_pageFileUpload"
    }
    vm_page {
        resource url: "/js/vm/PageVM.js"
        dependsOn "vm_unit", "ko_mapping", "unitUtils" , "vm_avatar"
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
        dependsOn "ko", "vm_owner", "vm_reply", "ko_timestamp"
    }
    vm_reply {
        resource url: "/js/vm/ReplyVM.js"
        dependsOn "ko", "vm_owner", "ko_timestamp"
    }
    vm_pageComments {
        resource url: "/js/vm/PageCommentsVM.js"
        dependsOn "ko", "vm_comment", "vm_reply"
    }
    vm_owner {
        resource url: "/js/vm/OwnerVM.js"
        dependsOn "ko", "ko_mapping", "vm_avatar"
    }


    /*   CUSTOM BINDINGS   */
    ko_pageFileUpload {
        resource url: "/js/ko/pageFileUpload.js"
        dependsOn "vendor_fileUpload", "ko"
    }
    ko_sortableInners {
        resource url: "/js/ko/sortableInners.js"
        resource url: "/css/sortable-inners.css"
        dependsOn "ko", "jqueryUi"
    }
    ko_fixFloat {
        resource url: "/js/ko/fixFloat.js"
        dependsOn "ko"
    }
    ko_autoResize {
        resource url: "/js/ko/autoResize.js"
        resource url: "/js/vendor/autoResize.js"
        dependsOn "ko", "jquery"
    }
    ko_carousel {
        resource url: "/js/ko/carousel.js"
        dependsOn "ko", "jquery", "vendor_bootstrap"
    }
    ko_fadeOut {
        resource url: "/js/ko/fadeOut.js"
        dependsOn "ko", "jquery"
    }
    ko_timestamp {
        resource url: "/js/ko/timestamp.js"
        dependsOn "ko", "jquery"
    }
    ko_avatarUpload {
        resource url: "/js/ko/avatarUpload.js"
        resource url: "/css/avatar-upload.css"
        dependsOn "ko", "vendor_fileUpload", "vm_avatar"
    }
    ko_autocomplete {
        resource url: "/js/ko/autocomplete.js"
        dependsOn "ko", "jqueryUi"
    }
    ko_ctrlEnter {
        resource url: "/js/ko/ctrlEnter.js"
        dependsOn "ko", "jquery"
    }

    unitUtils {
        resource url: "/js/UnitUtils.js"
    }

    mirariUnitAdd {
        resource url: "/css/unit-add.css"
        dependsOn "vm_pageEdit"
    }
    ko {
        resource url: "/js/vendor/ko/knockout-2.1.0.pre.js"
    }
    ko_mapping {
        resource url: "/js/vendor/ko/knockout-mapping.2.0.3.js"
        dependsOn "ko"
    }
    ko_editables {
        resource url: "/js/vendor/ko/ko.editables.js"
        dependsOn "ko"
    }

    aloha {
        resource url: "/js/ko/aloha.js"
        dependsOn "jquery", "ko"
    }
}