@Typed package mirari.morphia.subject

import com.google.code.morphia.annotations.Entity
import com.google.code.morphia.annotations.Id
import com.google.code.morphia.annotations.Reference
import org.bson.types.ObjectId

/**
 * @author Dmitry Kurinskiy
 * @since 10/1/11 1:46 PM
 */
@Entity
class SubjectInfo {
  @Id ObjectId id

  String frontText = ""

  @Reference(lazy = true)
  Subject subject
}
