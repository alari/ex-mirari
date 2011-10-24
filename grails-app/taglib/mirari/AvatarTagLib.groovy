package mirari

import mirari.morphia.subject.Person
import mirari.morphia.subject.Subject
import mirari.morphia.subject.PersonDAO
import mirari.morphia.subject.SubjectDAO

class AvatarTagLib {
  static namespace = "avatar"

  def springSecurityService
  def avatarService
  SubjectDAO subjectDao
  PersonDAO personDao

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
