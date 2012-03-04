(function() {
  var exports,
    __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; };

  exports = this;

  exports.AvatarVM = (function() {

    function AvatarVM() {
      this.fromJson = __bind(this.fromJson, this);      this.id = "";
      this.srcLarge = "";
      this.srcFeed = "";
      this.srcThumb = "";
      this.name = "";
      this.basic = true;
    }

    AvatarVM.prototype.fromJson = function(json) {
      this.id = json.id;
      this.srcLarge = json.srcLarge;
      this.srcFeed = json.srcFeed;
      this.srcThumb = json.srcThumb;
      this.name = json.name;
      this.basic = json.basic;
      return this;
    };

    return AvatarVM;

  })();

}).call(this);
