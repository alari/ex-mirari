(function() {
  var exports,
    __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; };

  exports = this;

  exports.ImageVM = (function() {

    function ImageVM() {
      this.fromJson = __bind(this.fromJson, this);      this.thumbSrc = "";
      this.smallSrc = "";
      this.mediumSrc = "";
      this.standardSrc = "";
      this.maxSrc = "";
    }

    ImageVM.prototype.fromJson = function(json) {
      this.thumbSrc = json.thumbSrc;
      this.smallSrc = json.smallSrc;
      this.mediumSrc = json.mediumSrc;
      this.standardSrc = json.standardSrc;
      this.maxSrc = json.maxSrc;
      return this;
    };

    return ImageVM;

  })();

}).call(this);
