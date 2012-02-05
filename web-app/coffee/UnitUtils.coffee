exports = this
class exports.UnitUtils
  @addUnitJson: (container, unitJson)->
    unit = new UnitVM(container, unitJson)
    if unitJson.inners and unitJson.inners.length
      @addUnitJson(unit, u) for u in unitJson.inners
    container.inners.push unit

  @addTextUnit: (container)->
    @addUnitJson container,
      type: "text"
      id: null
      text: ""
      title: null

  @addRenderInnersUnit: (container)->
    @addUnitJson container,
      type: "renderInners"
      id: null
      title: null

  @addExternalUnit: (container)->
    url = prompt("YouTube, Russia.Ru")
    return null if not url
    jsonPostReact "/p/addExternal", {url: url}, (mdl) =>
      @addUnitJson container, mdl

  @walk: (unit, fnc)->
    fnc(unit)
    @walk(u, fnc) for u in unit.inners()

  @isEmpty: (container)->
    for u in container.inners() when not u._destroy
      if not @isEmpty u
        return false
    if container instanceof UnitVM
      if container.type isnt "text"
        return false
      return not container.params.text
    true
