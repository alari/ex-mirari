(function() {
    $(function() {
        $("#unit-add-drop").fileupload({
            url: '/mirari/x/personPreferences/uploadAvatar',
            dataType: 'json',
            paramName: 'avatar',
            dropZone: $(this),
            add: function(e, data) {
                data.submit();
                $('#unit-add').animate({
                    height: 100
                }, 400, 'linear');
            },
            done: function(e, data) {
                $("#unit-content").animate(
                    {height: 500}, 400, 'linear', function() {

                        $("<img/>").attr("src", data.result.thumbnail + "?" + new Date().getTime() + new Date().getUTCMilliseconds()).appendTo($("#unit-content"));
                    }
                );


            }
        });
    });
})();