(function() {
  var exports,
    __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; };

  exports = this;

  exports.CommentVM = (function() {

    function CommentVM(_parent) {
      this._parent = _parent;
      this.fromJson = __bind(this.fromJson, this);
      this.url = __bind(this.url, this);
      this.clear = __bind(this.clear, this);
      this.id = null;
      this.text = "";
      this.html = "";
      this.owner = new OwnerVM();
      this.title = "";
      this.dateCreated = null;
      this.replies = ko.observableArray([]);
      this.canPostReply = this._parent.canPostComment;
      this.newReplyText = ko.observable("");
    }

    CommentVM.prototype.clear = function() {
      return this.newReplyText("");
    };

    CommentVM.prototype.url = function(action) {
      return this._parent.url(action);
    };

    CommentVM.prototype.isCurrentProfileId = function(id) {
      return this._parent._profileId === id;
    };

    CommentVM.prototype.isCurrentPageOwner = function() {
      return this._parent.isPageOwner;
    };

    CommentVM.prototype.canRemove = function() {
      return this.isCurrentProfileId(this.owner.id) || this.isCurrentPageOwner();
    };

    CommentVM.prototype.remove = function() {
      var _this = this;
      return jsonPostReact(this._parent.url("removeComment"), {
        commentId: this.id
      }, function(mdl) {
        return _this._parent.comments.remove(_this);
      });
    };

    CommentVM.prototype.fromJson = function(json) {
      var r, _i, _len, _ref, _ref2;
      this.id = json.id;
      this.text = json.text;
      this.html = json.html;
      this.owner.fromJson(json.owner);
      this.title = json.title;
      this.dateCreated = json.dateCreated;
      if ((_ref = json.replies) != null ? _ref.length : void 0) {
        _ref2 = json.replies;
        for (_i = 0, _len = _ref2.length; _i < _len; _i++) {
          r = _ref2[_i];
          this.replies.push(new ReplyVM(this).fromJson(r));
        }
      }
      return this;
    };

    CommentVM.prototype.postReply = function() {
      var _this = this;
      if (!this.newReplyText()) return null;
      return jsonPostReact(this._parent.url("postReply"), {
        commentId: this.id,
        text: this.newReplyText()
      }, function(mdl) {
        _this.clear();
        return _this.replies.push(new ReplyVM(_this).fromJson(mdl.reply));
      });
    };

    return CommentVM;

  })();

}).call(this);
