$(function(){
    //attach meta-data
    ko.bindingHandlers.sortableItem = {
        init: function(element, valueAccessor) {
            $(element).data("sortItem", valueAccessor());
        }
    };

    //connect items with observableArrays
    ko.bindingHandlers.sortableInners = {
        init: function(element, valueAccessor, allBindingsAccessor, context) {
            $(element).data("sortUnit", valueAccessor()); //attach meta-data
            $(element).sortable({
                update: function(event, ui) {
                    var item = ui.item.data("sortItem");
                    if (item) {
                        //identify parent
                        var newParent = ui.item.parent().data("sortUnit");
                        //figure out its new position
                        var position = ko.utils.arrayIndexOf(ui.item.parent().children(), ui.item[0]);
                        item.sortTo(newParent, position);
                    }
                },
                handle: "span.sort",
                connectWith: '.sortable'
            });
        }
    };
});