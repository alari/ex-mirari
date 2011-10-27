package mirari

import mirari.morphia.space.subject.Person
import mirari.morphia.space.Subject
import org.springframework.beans.factory.annotation.Autowired

class SubjectTagLib {
  static namespace = "sbj"

  def springSecurityService
  @Autowired Subject.Dao subjectDao
  @Autowired Person.Dao personDao

  def link = { attrs ->
    Subject subject = null
    if (attrs.subject instanceof Subject) subject = attrs.subject;
    else if (attrs.id) subject = subjectDao.getById(attrs.id.toString())
    else if (springSecurityService.isLoggedIn()) subject = personDao.getById(springSecurityService.principal.id?.toString())

    if (!subject) {
      out << "no subject to link"
    } else {
      out << g.link(controller: "space", action: "", params: [name: subject.name], subject.toString())
    }
  }
}
