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

    for (var el in this.elems) {
        $(el).data("unit-context", this);
    }
}
UnitEditContext.prototype = {
    data: {},

    buildUnitAdder: function() {

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
new UnitEditContext("#unit");