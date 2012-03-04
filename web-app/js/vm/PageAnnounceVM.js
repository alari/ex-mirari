(function() {
  var exports,
    __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; };

  exports = this;

  exports.PageAnnounceVM = (function() {

    function PageAnnounceVM(_parent, json) {
      this._parent = _parent;
      this.id = json.id;
      this.thumbSrc = json.thumbSrc;
      this.url = json.url;
      this.title = json.title;
      this.type = json.type;
      this.typeString = ko.observable(this._parent.typeToString(this.type));
      this.owner = new OwnerVM().fromJson(json.owner);
    }

    return PageAnnounceVM;

  })();

  exports.PageAnnounces = (function() {

    function PageAnnounces() {
      this.loadJson = __bind(this.loadJson, this);      this.pages = ko.observableArray([]);
      this.types = {};
    }

    PageAnnounces.prototype.typeToString = function(type) {
      return this.types[type];
    };

    PageAnnounces.prototype.loadJson = function(url) {
      var _this = this;
      return jsonGetReact(url, function(json) {
        var p, _i, _len, _ref, _results;
        _this.types = json.types;
        _ref = json.pages;
        _results = [];
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          p = _ref[_i];
          _results.push(_this.pages.push(new PageAnnounceVM(p)));
        }
        return _results;
      });
    };

    return PageAnnounces;

  })();

}).call(this);
