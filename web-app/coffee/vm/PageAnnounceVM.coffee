exports = this
class exports.PageAnnounceVM
  constructor: (@_parent, json)->
    @id = json.id

    @image = new ImageVM().fromJson(json.image)
    @url = json.url
    @title = json.title

    @type = json.type
    @typeString = ko.observable @_parent.typeToString(@type)

    @owner = new OwnerVM().fromJson(json.owner)

class exports.PageAnnounces
  constructor: ->
    @pages = ko.observableArray []
    @types = {}

  typeToString: (type)->
    @types[type]

  loadJson: (url)=>
    jsonGetReact url, (json)=>
      @types = json.types
      for p in json.pages
        @pages.push new PageAnnounceVM(p)