(function() {
  var exports;

  exports = this;

  exports.ReplyVM = (function() {

    function ReplyVM(_parent) {
      this._parent = _parent;
      this.id = "";
      this.html = "";
      this.owner = "";
      this.dateCreated = "";
    }

    ReplyVM.prototype.fromJson = function(json) {
      this.id = json.id;
      this.html = json.html;
      this.owner = json.owner;
      this.dateCreated = json.dateCreated;
      return this;
    };

    return ReplyVM;

  })();

}).call(this);
