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

  @addExternalUnit: (container)->
    url = prompt("YouTube, Russia.Ru")
    return null if not url
    $.ajax "/p/addExternal",
      type: "post"
      dataType: "json"
      data:
        url: url
      success: (data, textStatus, jqXHR) =>
        serviceReact data, (mdl) =>
          @addUnitJson container, mdl
      error: (data, textStatus, jqXHR)->
        alert "Error"

  @walk: (unit, fnc)->
    fnc(unit)
    @walk(u, fnc) for u in unit.inners()