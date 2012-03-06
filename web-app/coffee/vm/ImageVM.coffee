exports = this
class exports.ImageVM
  constructor: ->
    @thumbSrc = ""
    @smallSrc = ""
    @mediumSrc = ""
    @standardSrc = ""
    @maxSrc = ""


  fromJson: (json)=>
    @thumbSrc = json.thumbSrc
    @smallSrc = json.smallSrc
    @mediumSrc = json.mediumSrc
    @standardSrc = json.standardSrc
    @maxSrc = json.maxSrc

    this