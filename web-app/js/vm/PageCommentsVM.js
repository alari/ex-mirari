(function() {
  var NewComment, exports,
    __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; };

  exports = this;

  exports.PageCommentsVM = (function() {

    function PageCommentsVM(pageUrl, ownerId, _profileId, canPostComment) {
      var _this = this;
      this.pageUrl = pageUrl;
      this._profileId = _profileId;
      this.canPostComment = canPostComment;
      this.isPageOwner = this._profileId === ownerId;
      jsonGetReact(this.url("commentsVM"), function(mdl) {
        return _this.fromJson(mdl);
      });
      this.newComment = new NewComment(this);
    }

    PageCommentsVM.prototype.comments = ko.observableArray([]);

    PageCommentsVM.prototype.isPageOwner = false;

    PageCommentsVM.prototype.fromJson = function(json) {
      var c, _i, _len, _ref, _ref2;
      if ((_ref = json.comments) != null ? _ref.length : void 0) {
        _ref2 = json.comments;
        for (_i = 0, _len = _ref2.length; _i < _len; _i++) {
          c = _ref2[_i];
          this.pushComment(c);
        }
      }
      return this;
    };

    PageCommentsVM.prototype.pushComment = function(json) {
      return this.comments.push(new CommentVM(this).fromJson(json));
    };

    PageCommentsVM.prototype.url = function(action) {
      return this.pageUrl + "/" + action;
    };

    return PageCommentsVM;

  })();

  NewComment = (function() {

    function NewComment(vm) {
      this.vm = vm;
      this.post = __bind(this.post, this);
      this.clear = __bind(this.clear, this);
      this.title = ko.observable("");
      this.text = ko.observable("");
    }

    NewComment.prototype.clear = function() {
      this.title("");
      return this.text("");
    };

    NewComment.prototype.post = function() {
      var _this = this;
      if (!this.text().length) return false;
      return jsonPostReact(this.vm.url("postComment"), {
        title: this.title(),
        text: this.text()
      }, function(mdl) {
        _this.clear();
        return _this.vm.pushComment(mdl.comment);
      });
    };

    return NewComment;

  })();

}).call(this);
