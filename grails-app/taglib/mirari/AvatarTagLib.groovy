package mirari

import mirari.morphia.space.subject.Person
import mirari.morphia.space.Subject

class AvatarTagLib {
  static namespace = "avatar"

  def springSecurityService
  def avatarService
  Subject.Dao subjectDao
  Person.Dao personDao

  def large = {attrs->
    Subject subject
    if (attrs.subject instanceof Subject) subject = attrs.subject;
    else if (attrs.id) subject = subjectDao.getById(attrs.id.toString())
    else if (springSecurityService.isLoggedIn()) subject = personDao.getById(springSecurityService.principal.id?.toString())

    String url = avatarService.getUrl(subject, ImageFormat.AVATAR_LARGE)
    String upload = attrs.upload

    out << g.render( template: "/includes/largeAvatar", model: [url: url, upload: upload] )
  }
}
