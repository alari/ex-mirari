(function() {
  var exports,
    __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; };

  exports = this;

  exports.CommentVM = (function() {

    function CommentVM(_parent) {
      this._parent = _parent;
      this.fromJson = __bind(this.fromJson, this);
      this.clear = __bind(this.clear, this);
      this.id = null;
      this.text = ko.observable("");
      this.html = "";
      this.owner = null;
      this.title = "";
      this.dateCreated = null;
      this.replies = ko.observableArray([]);
      this.canPostReply = this._parent.canPostComment;
      this.newText = ko.observable("");
    }

    CommentVM.prototype.clear = function() {
      return this.newText("");
    };

    CommentVM.prototype.fromJson = function(json) {
      var r, _i, _len, _ref, _ref2;
      this.id = json.id;
      this.text(json.text);
      this.html = json.html;
      this.owner = json.owner;
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
      if (!this.newText()) return null;
      return $.ajax(this._parent.url("postReply"), {
        type: "post",
        dataType: "json",
        data: {
          commentId: this.id,
          text: this.newText()
        },
        success: function(data, textStatus, jqXHR) {
          return exports.serviceReact(data, function(mdl) {
            _this.clear();
            return _this.replies.push(new ReplyVM().fromJson(mdl));
          });
        },
        error: function(data, textStatus, jqXHR) {
          return alert("Error");
        }
      });
    };

    return CommentVM;

  })();

}).call(this);
