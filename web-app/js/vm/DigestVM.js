(function() {
  var NoticeVM, ReasonVM, exports;

  exports = this;

  NoticeVM = (function() {

    function NoticeVM() {
      this.id = "";
      this.reason = new ReasonVM(this);
      this.watched = ko.observable(false);
      this.type = "";
      this.date = "";
    }

    NoticeVM.prototype.fromJson = function(json) {
      this.id = json.id;
      this.date = json.date;
      this.type = json.type;
      this.reason.fromJson(json.reason);
      this.watched(json.watched);
      return this;
    };

    return NoticeVM;

  })();

  ReasonVM = (function() {

    function ReasonVM(notice) {
      this.notice = notice;
      this.actor = new OwnerVM;
      this.type = "";
    }

    ReasonVM.prototype.fromJson = function(json) {
      this.actor.fromJson(json.actor);
      return this.type = this.notice.type;
    };

    return ReasonVM;

  })();

  exports.DigestVM = (function() {

    function DigestVM() {
      this.notices = ko.observableArray([]);
    }

    DigestVM.prototype.load = function() {
      var _this = this;
      return jsonGetReact("/i/viewModel", function(json) {
        var n, _i, _len, _ref, _results;
        _ref = json.notices;
        _results = [];
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          n = _ref[_i];
          _results.push(_this.notices.push(new NoticeVM().fromJson(n)));
        }
        return _results;
      });
    };

    return DigestVM;

  })();

}).call(this);
