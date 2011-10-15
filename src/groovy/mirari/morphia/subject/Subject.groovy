@Typed package mirari.morphia.subject

import com.google.code.morphia.annotations.Entity
import com.google.code.morphia.annotations.Id
import com.google.code.morphia.annotations.Indexed
import com.google.code.morphia.annotations.PrePersist
import org.bson.types.ObjectId

/**
 * @author Dmitry Kurinskiy
 * @since 10/1/11 1:43 PM
 */
@Entity("subjects")
abstract class Subject {
  @Id ObjectId id

  @Indexed(unique = true)
  String domain

  Date dateCreated = new Date()
  Date lastUpdated

  @PrePersist
  void prePersist() {
    lastUpdated = new Date();
  }

  boolean isPerson() {
    this.class == mirari.morphia.subject.Person
  }
}
