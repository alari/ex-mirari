exports = this
class exports.ReplyVM
  constructor: (@_parent)->
    @id = ""
    @html = ""
    @owner = ""
    @dateCreated = ""

  fromJson: (json)->
    @id = json.id
    @html = json.html
    @owner = json.owner
    @dateCreated = json.dateCreated
    this