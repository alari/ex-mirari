(function() {
  var exports,
    __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; };

  exports = this;

  exports.PageCommentsVM = (function() {

    function PageCommentsVM(pageUrl, showAddForm) {
      var _this = this;
      this.pageUrl = pageUrl;
      this.showAddForm = showAddForm;
      this.clear = __bind(this.clear, this);
      this.comments = ko.observableArray([]);
      this.newTitle = ko.observable("");
      this.newText = ko.observable("");
      $.ajax(this.url("commentsVM"), {
        type: "get",
        dataType: "json",
        success: function(data, textStatus, jqXHR) {
          return exports.serviceReact(data, function(mdl) {
            return _this.fromJson(mdl);
          });
        },
        error: function(data, textStatus, jqXHR) {
          return alert("Error");
        }
      });
    }

    PageCommentsVM.prototype.fromJson = function(json) {
      var c, _i, _len, _ref;
      _ref = json.comments;
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        c = _ref[_i];
        this.comments.push(new CommentVM(this).fromJson(c));
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
      return $.ajax(this.url("postComment"), {
        type: "post",
        dataType: "json",
        data: {
          title: this.newTitle(),
          text: this.newText()
        },
        success: function(data, textStatus, jqXHR) {
          return exports.serviceReact(data, function(mdl) {
            _this.clear();
            return _this.comments.push(new CommentVM().fromJson(mdl));
          });
        },
        error: function(data, textStatus, jqXHR) {
          return alert("Error");
        }
      });
    };

    return PageCommentsVM;

  })();

}).call(this);
