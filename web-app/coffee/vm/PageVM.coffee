exports = this
class exports.PageVM
  constructor: ->
    @inners = ko.observableArray []

    @tags = ko.observableArray []

    @title = ko.observable ""

    @id = ko.observable ""

    @type = ko.observable "page"

    @draft = ko.observable true
    @thumbSrc = ko.observable ""

    @innersCount = ko.computed =>
      (u for u in @inners() when not u._destroy).length

    @avatar = new AvatarVM()

    @tagAct = new TagEditAct(this) if TagEditAct?
    @editAct = new PageEditAct(this)
    @innersAct = new PageInnersAct(this)

  unitTmpl: (unit)->
    "edit_"+unit.type

  toJson: ->
    ko.mapping.toJSON this,
      ignore: ["_parent", "toJson", "avatar", "innersCount", "innersVisible", "contentVisible", "tagAct", "editAct", "innersAct"]

  fromJson: (json)->
    @inners.removeAll()
    @tags.removeAll()

    @title json.title
    @id json.id
    @type json.type
    @draft json.draft

    @thumbSrc json.thumbSrc

    @innersAct.addUnit(u) for u in json.inners
    @tagAct.addTag(t) for t in json.tags

#
#       Actions to do with Inner Units of the Page
#
class PageInnersAct
  constructor: (@vm)->

  addUnit: (unitJson)=>
    UnitUtils.addUnitJson @vm, unitJson

  addExternalUnit: =>
    UnitUtils.addExternalUnit @vm

  addTextUnit: =>
    UnitUtils.addTextUnit @vm

  addRenderInnersUnit: =>
    UnitUtils.addRenderInnersUnit @vm

  hideAllInners: =>
    UnitUtils.walk(node, (n)-> n.innersVisible(false) if n.innersCount() > 0) for node in @vm.inners()

  showAllInners: =>
    UnitUtils.walk(node, (n)-> n.innersVisible(true) if n.innersCount() > 0) for node in @vm.inners()

  hideAllContent: =>
    UnitUtils.walk(node, (n)-> n.contentVisible(false)) for node in @vm.inners()

  showAllContent: =>
    UnitUtils.walk(node, (n)-> n.contentVisible(true)) for node in @vm.inners()

#
#       Actions to do with Page whilst edit
#
class PageEditAct
  constructor: (@vm)->

  saveAndContinue: =>
      return false if UnitUtils.isEmpty @vm
      _t = @vm
      jsonPostReact "saveAndContinue", {ko: @vm.toJson()}, (mdl) =>
        _t.fromJson(mdl.page)

  submitDraft: =>
      @vm.draft true
      @submit()

  submitPub: =>
      @vm.draft false
      @submit()

  submit: =>
      return false if UnitUtils.isEmpty @vm
      jsonPostReact "save", {draft: @vm.draft(), ko: @vm.toJson()}, (mdl) ->
        console.log mdl
