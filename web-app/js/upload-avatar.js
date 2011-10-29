(function() {
    $(function() {
        $("*[data-avatar]").each(function() {

            $(this).find('.avatar-progressbar').hide();
            $(this).fileupload({
                url: $(this).data("avatar"),
                dataType: "json",
                paramName: "avatar",
                dropZone: $(this),
                add: function (e, data) {
                    data.submit();
                },
                done: function(e, data) {
                    $("img", this).attr("src", data.result.thumbnail + "?" + new Date().getTime() + new Date().getUTCMilliseconds());

                },
                fail: function(e, data) {
                    alert(data.result);
                },

                // Callback for upload progress events:
                progress: function (e, data) {
                    $(this).find('.ui-progressbar').progressbar(
                        'value',
                        parseInt(data.loaded / data.total * 100, 10)
                    );
                },

                // Callback for uploads start, equivalent to the global ajaxStart event:
                start: function () {
                    $(this).find('.ui-progressbar')
                        .progressbar('value', 0).fadeIn();
                },
                // Callback for uploads stop, equivalent to the global ajaxStop event:
                stop: function () {
                    $(this).find('.ui-progressbar').fadeOut();
                    $(this).removeClass("avatar-dragover");
                },

                dragover: function() {
                    $(this).addClass("avatar-dragover");
                },

                drop: function() {
                    $(this).removeClass("avatar-dragover");
                }


            });
            $(this).bind("mouseover", function() {
                $(this).removeClass("avatar-dragover");
            });
            $(this).bind("mouseout", function() {
                $(this).removeClass("avatar-dragover");
            });
        });
    });
})();