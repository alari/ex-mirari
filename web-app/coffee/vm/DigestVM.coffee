exports = this
class NoticeVM
  constructor: ->
    @id = ""
    @reason = new ReasonVM(this)
    @watched = ko.observable false
    @type = ""
    @date = ""

  fromJson: (json)->
    @id = json.id
    @date = json.date
    @type = json.type
    @reason.fromJson(json.reason)
    @watched json.watched
    this

class ReasonVM
  constructor: (@notice)->
    @actor = new OwnerVM
    @type = ""

  fromJson: (json)->
    @actor.fromJson(json.actor)
    @type = @notice.type

class exports.DigestVM
  constructor: ->
    @notices = ko.observableArray []

  load: ->
    jsonGetReact "/i/viewModel", (json)=>
      @notices.push new NoticeVM().fromJson(n) for n in json.notices