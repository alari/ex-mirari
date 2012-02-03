(function() {
  var exports,
    __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; };

  exports = this;

  exports.PageCommentsVM = (function() {

    function PageCommentsVM(pageUrl, canPostComment) {
      var _this = this;
      this.pageUrl = pageUrl;
      this.canPostComment = canPostComment;
      this.clear = __bind(this.clear, this);
      jsonGetReact(this.url("commentsVM"), function(mdl) {
        return _this.fromJson(mdl);
      });
    }

    PageCommentsVM.prototype.newTitle = ko.observable("");

    PageCommentsVM.prototype.newText = ko.observable("");

    PageCommentsVM.prototype.comments = ko.observableArray([]);

    PageCommentsVM.prototype.fromJson = function(json) {
      var c, _i, _len, _ref, _ref2;
      if ((_ref = json.comments) != null ? _ref.length : void 0) {
        _ref2 = json.comments;
        for (_i = 0, _len = _ref2.length; _i < _len; _i++) {
          c = _ref2[_i];
          this.comments.push(new CommentVM(this).fromJson(c));
        }
      }
      return this;
    };

    PageCommentsVM.prototype.url = function(action) {
      return this.pageUrl + "/" + action;
    };

    PageCommentsVM.prototype.clear = function() {
      this.newTitle("");
      return this.newText("");
    };

    PageCommentsVM.prototype.postComment = function() {
      var _this = this;
      if (!this.newText().length) return false;
      return jsonPostReact(this.url("postComment"), {
        title: this.newTitle,
        text: this.newText
      }, function(mdl) {
        _this.clear();
        return _this.comments.push(new CommentVM().fromJson(mdl));
      });
    };

    return PageCommentsVM;

  })();

}).call(this);
