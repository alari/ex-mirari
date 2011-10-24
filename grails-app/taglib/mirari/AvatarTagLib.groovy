package mirari

import mirari.morphia.subject.Person

class AvatarTagLib {
  static namespace = "avatar"

  def springSecurityService
  def avatarService

  def large = {attrs->
    String url = avatarService.getUrl(attrs.for instanceof Person ? attrs.for : springSecurityService.currentUser, ImageFormat.AVATAR_LARGE)
    String upload = attrs.upload

    g.render template: "/includes/largeAvatar", model: [url: url, upload: upload]
  }
}
