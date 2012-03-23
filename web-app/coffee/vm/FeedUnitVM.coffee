exports = this
class exports.FeedUnitVM extends PageAnnounces
  constructor: (@baseUrl, @style, @lastStyle)->
    super()
    @last = ko.observable null
    @lastTemplate = "announce_"+@lastStyle

    @page = ko.observable 0
    @pagesTemplate = "announces_"+@style

    @hasMorePages = ko.observable true

  fromJson: (json)=>
    @types = json.types if json.types
    @pages.push new PageAnnounceVM(this, p) for p in json.pages
    @last new PageAnnounceVM(this, json.last) if json.last

  loadJson: (page)=>
    jsonGetReact @baseUrl + "?page=" + page, (json)=>
      @hasMorePages json.pages?.length
      @fromJson(json)
      @page page

  loadPage: =>
    @loadJson @page()+1