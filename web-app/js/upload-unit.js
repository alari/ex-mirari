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
                $("#unit-content")
                    .html("<img id='unit-img' style='display:none' src=\"" + data.result.thumbnail + "?" + new Date().getTime() + new Date().getUTCMilliseconds() + "\"/>");
                $("#unit-content").animate({height: '440px'}, 400, 'linear', function(){
                    $("#unit-img").fadeIn(400);
                });
            }
        });
    });
})();

$.fn.getUnitState = function(){
    return $(this).data("unit-state") ? $(this).data("unit-state") : false;
};