exports = this

class exports.CompoundUnitVM

  constructor: (@unit)->
    @type = @unit.params.type
    if not @unit.innersCount()
      CompoundType[@type].init @unit, this

  getInner: (type)=>
    for u in @unit.inners()
      if u.type == type
        return u

class exports.CompoundType
  @poetry:
    init: (unit, compound)->
      UnitUtils.addTextUnit(@unit)