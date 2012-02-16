exports = this
class exports.TagVM
  constructor: (@_parent, @displayName) ->
    @id = null

  fromJson: (json) =>
    @id = json.id
    @displayName = json.displayName
    this

  remove: =>
    @_parent.tags.remove this