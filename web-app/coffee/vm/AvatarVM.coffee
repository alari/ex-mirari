exports = this
class exports.AvatarVM
  constructor: ->
    @id = ""

    @srcLarge = ""
    @srcFeed = ""
    @srcThumb = ""

    @name = ""
    @basic = true

  fromJson: (json)=>
    @id = json.id

    @srcLarge = json.srcLarge
    @srcFeed = json.srcFeed
    @srcThumb = json.srcThumb

    @name = json.name
    @basic = json.basic

    this