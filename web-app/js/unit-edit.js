function UnitEditContext(unitEnvelop) {
    this.envelop = $(unitEnvelop);
    this.action = this.envelop.data("unit-action");
    this.elems = {};

    this.elems.title = $(".unit-title", this.envelop);
    this.elems.title.change(this.titleChange.bind(this));
    this.data.title = this.elems.title.value;

    this.elems.buttonPub = $(".unit-pub", this.envelop);
    this.elems.buttonPub.click(this.submitPublish.bind(this));

    this.elems.buttonDraft = $(".unit-draft", this.envelop);
    this.elems.buttonDraft.click(this.submitDraft.bind(this));

    this.elems.unitAdder = $(".unit-adder", this.envelop);
    this.buildUnitAdder();

    this.elems.content = $(".unit-content", this.envelop);

    for (var el in this.elems) {
        $(el).data("unit-context", this);
    }
}
UnitEditContext.prototype = {
    data: {unitId:null},

    buildUnitAdder: function() {
        this.elems.unitAdder.find("form").fileupload({
            dataType: "json",
            dropZone: this.elems.unitAdder.find("unit-adder-drop"),
            add: function (e, data) {
                data.container = this.data.unitId;
                data.submit();
            }.bind(this),
            done: function(e, data) {
                serviceReact(data.result, "#alerts", function(mdl) {
                    console.log(mdl);
                    this.data.unitId = mdl.id;
                    this.elems.content.append("<div data-unit-id='"+mdl.id
                    +"'><img src='"+mdl.srcPage+"'/><br/><img src='"+mdl.srcFeed+"'/><br/><img src='"+mdl.srcTiny
                        +"'/><br/><a target='_blank' href='"+mdl.srcMax+"'>link to max</a></div>");
                    this.elems.unitAdder.animate({
                        height: 100
                    }, 400, 'linear');
                }.bind(this));
            }.bind(this)
        });
    },

    titleChange: function(eventObject) {
        this.data.title = eventObject.currentTarget.value;
    },
    submitPublish: function() {
        this.data.draft = false;
        this.submit();
    },
    submitDraft: function() {
        this.data.draft = true;
        this.submit();
    },
    submit: function() {
        $.ajax(this.action, {
            type: "post",
            dataType: "json",
            data: this.data,
            success: function(data, textStatus, jqXHR) {
                serviceReact(data, "#alerts", function(mdl) {
                    console.log(mdl);
                });
            },
            error: function() {
                alert("Error");
            }
        });
    }
};
var uec = new UnitEditContext("#unit");