(function() {
  var exports,
    __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; },
    __hasProp = Object.prototype.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor; child.__super__ = parent.prototype; return child; };

  exports = this;

  exports.FeedUnitVM = (function() {

    __extends(FeedUnitVM, PageAnnounces);

    function FeedUnitVM(baseUrl, style, lastStyle) {
      this.baseUrl = baseUrl;
      this.style = style;
      this.lastStyle = lastStyle;
      this.loadPage = __bind(this.loadPage, this);
      this.loadJson = __bind(this.loadJson, this);
      this.fromJson = __bind(this.fromJson, this);
      FeedUnitVM.__super__.constructor.call(this);
      this.last = ko.observable(null);
      this.lastTemplate = "announce_" + this.lastStyle;
      this.page = ko.observable(0);
      this.pagesTemplate = "announces_" + this.style;
      this.hasMorePages = ko.observable(true);
    }

    FeedUnitVM.prototype.fromJson = function(json) {
      var p, _i, _len, _ref;
      if (json.types) this.types = json.types;
      _ref = json.pages;
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        p = _ref[_i];
        this.pages.push(new PageAnnounceVM(this, p));
      }
      if (json.last) return this.last(new PageAnnounceVM(this, json.last));
    };

    FeedUnitVM.prototype.loadJson = function(page) {
      var _this = this;
      return jsonGetReact(this.baseUrl + "?page=" + page, function(json) {
        var _ref;
        _this.hasMorePages((_ref = json.pages) != null ? _ref.length : void 0);
        _this.fromJson(json);
        return _this.page(page);
      });
    };

    FeedUnitVM.prototype.loadPage = function() {
      return this.loadJson(this.page() + 1);
    };

    return FeedUnitVM;

  })();

}).call(this);
