(function() {
  var exports,
    __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; };

  exports = this;

  exports.CommentVM = (function() {

    function CommentVM() {
      this.fromJson = __bind(this.fromJson, this);      this.id = null;
      this.text = ko.observable("");
      this.html = "";
      this.owner = null;
      this.title = "";
      this.dateCreated = null;
    }

    CommentVM.prototype.fromJson = function(json) {
      this.id = json.id;
      this.text(json.text);
      this.html = json.html;
      this.owner = json.owner;
      this.title = json.title;
      return this.dateCreated = json.dateCreated;
    };

    return CommentVM;

  })();

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
      var c, cm, _i, _len, _ref, _results;
      _ref = json.comments;
      _results = [];
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        c = _ref[_i];
        cm = new CommentVM();
        cm.fromJson(c);
        _results.push(this.comments.push(cm));
      }
      return _results;
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
            var c;
            _this.clear();
            console.log(mdl);
            c = new CommentVM();
            c.fromJson(mdl);
            return _this.comments.push(c);
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
