package mirari

import org.springframework.beans.factory.annotation.Autowired
import mirari.morphia.subject.SubjectDAO
import mirari.morphia.subject.PersonDAO
import mirari.morphia.subject.Subject

class SubjectTagLib {
  static namespace = "sbj"

  def springSecurityService
  @Autowired SubjectDAO subjectDao
  @Autowired PersonDAO personDao

  def link = { attrs ->
    Subject subject = null
    if (attrs.subject instanceof Subject) subject = attrs.subject;
    else if (attrs.id) subject = subjectDao.getById(attrs.id.toString())
    else if (springSecurityService.isLoggedIn()) subject = personDao.getById(springSecurityService.principal.id?.toString())

    if (!subject) {
      out << "no subject to link"
    } else {
      out << g.link(controller: "subject", action: "", params: [domain: subject.domain], subject.domain+"(${subject.class.simpleName})")
    }
  }
}
