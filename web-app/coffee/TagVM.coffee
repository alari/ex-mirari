exports = this
class exports.TagVM
  constructor: (@_parent, @displayName) ->
    @id = null

  fromJSON: (json) =>
    @id = json.id
    @displayName = json.displayName
    this

  remove: =>
    @_parent.tags.remove this